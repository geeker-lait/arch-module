package org.arch.framework.jackson2.deserializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.arch.framework.enums.ChannelType;
import org.arch.framework.userdetails.ArchUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Set;

/**
 * {@link ArchUser} Jackson2 反序列化器.
 * @author YongWu zheng
 * @since 2021.1.3 19:41
 */
public class ArchUserDeserializer extends StdDeserializer<ArchUser> {

    private static final long serialVersionUID = 1877501002005077655L;

    public ArchUserDeserializer() {
        super(ArchUser.class);
    }

    @SuppressWarnings("DuplicateThrows")
    @Override
    public ArchUser deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        Set<? extends GrantedAuthority> authorities =
                mapper.convertValue(jsonNode.get("authorities"), new TypeReference<Set<SimpleGrantedAuthority>>() {});
        JsonNode password = this.readJsonNode(jsonNode, "password");
        String channelTypeString = this.readJsonNode(jsonNode, "channelType").asText("");
        ChannelType channelType;
        try {
            channelType = ChannelType.valueOf(channelTypeString.trim().toUpperCase());
        }
        catch (Exception e) {
            throw new InvalidFormatException(jp,
                                             "转换 ChannelType 时发生错误",
                                             channelTypeString,
                                             ChannelType.class);
        }
        ArchUser result = new ArchUser(this.readJsonNode(jsonNode, "username").asText(),
                                       password.asText(""),
                                       this.readJsonNode(jsonNode, "accountId").asLong(),
                                       channelType,
                                       this.readJsonNode(jsonNode, "enabled").asBoolean(),
                                       this.readJsonNode(jsonNode, "accountNonExpired").asBoolean(),
                                       this.readJsonNode(jsonNode, "credentialsNonExpired").asBoolean(),
                                       this.readJsonNode(jsonNode, "accountNonLocked").asBoolean(),
                                       authorities);

        if (password.asText(null) == null) {
            result.eraseCredentials();
        }

        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        if (jsonNode.has(field)) {
            return jsonNode.get(field);
        }
        return MissingNode.getInstance();
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
    @JsonDeserialize(using = ArchUserDeserializer.class)
    public interface ArchUserMixin {}

}
