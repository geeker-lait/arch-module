package org.arch.framework.ums.jackson2.deserializer;

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
import org.arch.framework.ums.enums.LoginType;
import org.arch.framework.ums.userdetails.ArchUser;
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
        String loginTypeString = this.readJsonNode(jsonNode, "loginType").asText("");
        JsonNode nickName = this.readJsonNode(jsonNode, "nickName");
        JsonNode avatar = this.readJsonNode(jsonNode, "avatar");
        int loginType;
        try {
            loginType = Integer.parseInt(loginTypeString.trim());
        }
        catch (Exception e) {
            throw new InvalidFormatException(jp,
                                             "转换 LoginType 时发生错误",
                                             loginTypeString,
                                             LoginType.class);
        }
        ArchUser result = new ArchUser(this.readJsonNode(jsonNode, "username").asText(),
                                       password.asText(""),
                                       this.readJsonNode(jsonNode, "identifierId").asLong(),
                                       this.readJsonNode(jsonNode, "accountId").asLong(),
                                       this.readJsonNode(jsonNode, "tenantId").asInt(),
                                       loginType,
                                       nickName.asText(""),
                                       avatar.asText(""),
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
