package org.arch.auth.jwt.service;

import org.arch.framework.ums.jwt.claim.JwtArchClaimNames;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.converter.ClaimConversionService;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import top.dcenter.ums.security.jwt.api.supplier.JwtClaimTypeConverterSupplier;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

/**
 * arch jwt claim set converter supplier.<br>
 * 默认自动通过 {@link MappedJwtClaimSetConverter#withDefaults(Map)} 配置.
 * <pre>
 *     MappedJwtClaimSetConverter.withDefaults(jwtClaimTypeConverterSupplier.getConverter());
 * </pre>
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 16:27
 */
public class ArchJwtClaimTypeConverterSupplier implements JwtClaimTypeConverterSupplier {

    private static final ConversionService CONVERSION_SERVICE = ClaimConversionService.getSharedInstance();

    private static final TypeDescriptor OBJECT_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(Object.class);

    private static final TypeDescriptor STRING_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(String.class);

    @SuppressWarnings("SameParameterValue")
    private static Converter<Object, ?> getConverter(TypeDescriptor targetDescriptor) {
        return (source) -> CONVERSION_SERVICE.convert(source, OBJECT_TYPE_DESCRIPTOR, targetDescriptor);
    }

    @SuppressWarnings({"SameParameterValue", "ConstantConditions"})
    private static Converter<Object, Collection<String>> getCollectionConverter(String delimiter) {
        return (source) -> Arrays.asList(ofNullable((String) source).orElse("").split(delimiter));
    }

    @Override
    @NonNull
    public Map<String, Converter<Object, ?>> getConverter() {
        Map<String, Converter<Object, ?>> map = new HashMap<>(16);
        map.put(JwtArchClaimNames.ACCOUNT_ID.getClaimName(), getConverter(STRING_TYPE_DESCRIPTOR));
        map.put(JwtArchClaimNames.NICK_NAME.getClaimName(), getConverter(STRING_TYPE_DESCRIPTOR));
        map.put(JwtArchClaimNames.AVATAR.getClaimName(), getConverter(STRING_TYPE_DESCRIPTOR));
        map.put(JwtArchClaimNames.CHANNEL_TYPE.getClaimName(), getConverter(STRING_TYPE_DESCRIPTOR));
        map.put(JwtArchClaimNames.TENANT_ID.getClaimName(), getConverter(STRING_TYPE_DESCRIPTOR));
        map.put(JwtArchClaimNames.AUTHORITIES.getClaimName(), getCollectionConverter(" "));
        map.put(JwtArchClaimNames.REFRESH_TOKEN_JTI.getClaimName(), getConverter(STRING_TYPE_DESCRIPTOR));
        return Collections.unmodifiableMap(map);
    }
}
