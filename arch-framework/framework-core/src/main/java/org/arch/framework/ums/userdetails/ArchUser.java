package org.arch.framework.ums.userdetails;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.ums.enums.ChannelType;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * 财智有道 {@link UserDetails}
 *
 * Models core user information retrieved by a {@link UserDetailsService}.
 * <p>
 * Developers may use this class directly, subclass it, or write their own
 * {@link UserDetails} implementation from scratch.
 * <p>
 * {@code equals} and {@code hashcode} implementations are based on the {@code username}
 * property only, as the intention is that lookups of the same user principal object (in a
 * user registry, for example) will match where the objects represent the same user, not
 * just when all the properties (authorities, password for example) are the same.
 * <p>
 * Note that this implementation is not immutable. It implements the
 * {@code CredentialsContainer} interface, in order to allow the password to be erased
 * after authentication. This may cause side-effects if you are storing instances
 * in-memory and reusing them. If so, make sure you return a copy from your
 * {@code UserDetailsService} each time it is invoked.
 *
 * @author Ben Alex
 * @author Luke Taylor
 * @author YongWu zheng
 */
@Slf4j
public class ArchUser implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名 ID
     */
    private final Long accountId;
    /**
     * 租户 ID
     */
    private Integer tenantId;
    /**
     * 登录类型【IDENTITY TYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private final ChannelType channelType;
    /**
     * 昵称
     */
    private final String nickName;
    /**
     * 头像
     */
    private final String avatar;
    /**
     * 对应与 AccountIdentifier 的 credential 字段,
     * 授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token
     */
    private String password;
    /**
     * 对应与 AccountIdentifier 的 identifier 字段,
     * 识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；
     */
    private final String username;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    // ~ Constructors
    // ===================================================================================================

    /**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     */
    public ArchUser(String username, String password,
                    Long accountId, Integer tenantId, ChannelType channelType,
                    String nickName, String avatar,
                    Collection<? extends GrantedAuthority> authorities) {
        this(username, password, accountId, tenantId, channelType, nickName, avatar,
             true, true, true, true, authorities);
    }

    /**
     * Construct the <code>ArchUser</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     *
     *
     * @param username the username presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param accountId     用户名 ID
     * @param tenantId      租户 ID
     * @param channelType   登录类型【IDENTITY TYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博
     * @param nickName      昵称
     * @param avatar        头像
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     * expired
     * @param accountNonLocked set to <code>true</code> if the account is not locked
     * @param authorities the authorities that should be granted to the caller if they
     * presented the correct username and password and the user is enabled. Not null.
     *
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     * a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public ArchUser(String username, String password,
                    Long accountId, Integer tenantId, ChannelType channelType,
                    String nickName, String avatar,
                    boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                    boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {

        requireNonNull(accountId, "accountId cannot be null");
        requireNonNull(tenantId, "tenantId cannot be null");
        requireNonNull(channelType, "channelType cannot be null");
        this.accountId = accountId;
        this.tenantId = tenantId;
        this.channelType = channelType;
        this.nickName = nickName;
        this.avatar = avatar;

        //noinspection AlibabaAvoidComplexCondition
        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    // ~ Methods
    // ========================================================================================================


    public Long getAccountId() {
        return accountId;
    }

    public Integer getTenantId() { return tenantId; }

    public ChannelType getChannelType() {
        return channelType;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(
                new ArchUser.AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                           "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    /**
     * Returns {@code true} if the supplied object is a {@code ArchUser} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing
     * the same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof ArchUser) {
            return username.equals(((ArchUser) rhs).username);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("AccountId: ").append(this.accountId.toString()).append("; ");
        sb.append("TenantId: ").append(this.tenantId.toString()).append("; ");
        sb.append("ChannelType: ").append(this.channelType.name()).append("; ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired)
          .append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        }
        else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    /**
     * Creates a UserBuilder with a specified user name
     *
     * @param username the username to use
     * @return the UserBuilder
     */
    public static ArchUser.UserBuilder withUsername(String username) {
        return builder().username(username);
    }

    /**
     * Creates a UserBuilder
     *
     * @return the UserBuilder
     */
    public static ArchUser.UserBuilder builder() {
        return new ArchUser.UserBuilder();
    }

    /**
     * <p>
     * <b>WARNING:</b> This method is considered unsafe for production and is only intended
     * for sample applications.
     * </p>
     * <p>
     * Creates a user and automatically encodes the provided password using
     * {@code PasswordEncoderFactories.createDelegatingPasswordEncoder()}. For example:
     * </p>
     *
     * <pre>
     * <code>
     * UserDetails user = ArchUser.withDefaultPasswordEncoder()
     *     .username("user")
     *     .password("password")
     *     .roles("USER")
     *     .build();
     * // outputs {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
     * System.out.println(user.getPassword());
     * </code>
     * </pre>
     *
     * This is not safe for production (it is intended for getting started experience)
     * because the password "password" is compiled into the source code and then is
     * included in memory at the time of creation. This means there are still ways to
     * recover the plain text password making it unsafe. It does provide a slight
     * improvement to using plain text passwords since the UserDetails password is
     * securely hashed. This means if the UserDetails password is accidentally exposed,
     * the password is securely stored.
     *
     * In a production setting, it is recommended to hash the password ahead of time.
     * For example:
     *
     * <pre>
     * <code>
     * PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
     * // outputs {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
     * // remember the password that is printed out and use in the next step
     * System.out.println(encoder.encode("password"));
     * </code>
     * </pre>
     *
     * <pre>
     * <code>
     * UserDetails user = ArchUser.withUsername("user")
     *     .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
     *     .roles("USER")
     *     .build();
     * </code>
     * </pre>
     *
     * @return a UserBuilder that automatically encodes the password with the default
     * PasswordEncoder
     * @deprecated Using this method is not considered safe for production, but is
     * acceptable for demos and getting started. For production purposes, ensure the
     * password is encoded externally. See the method Javadoc for additional details.
     * There are no plans to remove this support. It is deprecated to indicate
     * that this is considered insecure for production purposes.
     */
    @Deprecated
    public static ArchUser.UserBuilder withDefaultPasswordEncoder() {
        log.warn("User.withDefaultPasswordEncoder() is considered unsafe for production and is only intended for sample applications.");
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return builder().passwordEncoder(encoder::encode);
    }

    public static ArchUser.UserBuilder withUserDetails(ArchUser userDetails) {
        return withUsername(userDetails.getUsername())
                .password(userDetails.getPassword())
                .accountId(userDetails.getAccountId())
                .tenantId(userDetails.getTenantId())
                .channelType(userDetails.getChannelType())
                .accountExpired(!userDetails.isAccountNonExpired())
                .accountLocked(!userDetails.isAccountNonLocked())
                .authorities(userDetails.getAuthorities())
                .credentialsExpired(!userDetails.isCredentialsNonExpired())
                .disabled(!userDetails.isEnabled());
    }

    /**
     * Builds the user to be added. At minimum the username, password, and authorities
     * should provided. The remaining attributes have reasonable defaults.
     */
    public static class UserBuilder {
        private Long accountId;
        private Integer tenantId;
        private ChannelType channelType;
        private String nickName;
        private String avatar;
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private boolean disabled;
        private Function<String, String> passwordEncoder = password -> password;

        /**
         * Creates a new instance
         */
        private UserBuilder() {
        }

        /**
         * Populates the accountId. This attribute is required.
         *
         * @param accountId the accountId. Cannot be null.
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder accountId(Long accountId) {
            Assert.notNull(accountId, "accountId cannot be null");
            this.accountId = accountId;
            return this;
        }

        /**
         * Populates the tenantId. This attribute is required.
         *
         * @param tenantId the tenantId. Cannot be null.
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder tenantId(Integer tenantId) {
            Assert.notNull(tenantId, "tenantId cannot be null");
            this.tenantId = tenantId;
            return this;
        }
        /**
         * Populates the channelType. This attribute is required.
         *
         * @param channelType the channelType. Cannot be null.
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder channelType(ChannelType channelType) {
            Assert.notNull(channelType, "channelType cannot be null");
            this.channelType = channelType;
            return this;
        }
        /**
         * Populates the nickName. This attribute is required.
         *
         * @param nickName the nickName. Cannot be null.
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder nickName(String nickName) {
            Assert.notNull(nickName, "nickName cannot be null");
            this.nickName = nickName;
            return this;
        }
        /**
         * Populates the avatar. This attribute is required.
         *
         * @param avatar the avatar. Cannot be null.
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder avatar(String avatar) {
            Assert.notNull(avatar, "avatar cannot be null");
            this.avatar = avatar;
            return this;
        }
        /**
         * Populates the username. This attribute is required.
         *
         * @param username the username. Cannot be null.
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        /**
         * Populates the password. This attribute is required.
         *
         * @param password the password. Cannot be null.
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        /**
         * Encodes the current password (if non-null) and any future passwords supplied
         * to {@link #password(String)}.
         *
         * @param encoder the encoder to use
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder passwordEncoder(Function<String, String> encoder) {
            Assert.notNull(encoder, "encoder cannot be null");
            this.passwordEncoder = encoder;
            return this;
        }

        /**
         * Populates the roles. This method is a shortcut for calling
         * {@link #authorities(String...)}, but automatically prefixes each entry with
         * "ROLE_". This means the following:
         *
         * <code>
         *     builder.roles("USER","ADMIN");
         * </code>
         *
         * is equivalent to
         *
         * <code>
         *     builder.authorities("ROLE_USER","ROLE_ADMIN");
         * </code>
         *
         * <p>
         * This attribute is required, but can also be populated with
         * {@link #authorities(String...)}.
         * </p>
         *
         * @param roles the roles for this user (i.e. USER, ADMIN, etc). Cannot be null,
         * contain null values or start with "ROLE_"
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder roles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList<>(
                    roles.length);
            for (String role : roles) {
                Assert.isTrue(!role.startsWith("ROLE_"), () -> role
                        + " cannot start with ROLE_ (it is automatically added)");
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return authorities(authorities);
        }

        /**
         * Populates the authorities. This attribute is required.
         *
         * @param authorities the authorities for this user. Cannot be null, or contain
         * null values
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         * @see #roles(String...)
         */
        public ArchUser.UserBuilder authorities(GrantedAuthority... authorities) {
            return authorities(Arrays.asList(authorities));
        }

        /**
         * Populates the authorities. This attribute is required.
         *
         * @param authorities the authorities for this user. Cannot be null, or contain
         * null values
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         * @see #roles(String...)
         */
        public ArchUser.UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList<>(authorities);
            return this;
        }

        /**
         * Populates the authorities. This attribute is required.
         *
         * @param authorities the authorities for this user (i.e. ROLE_USER, ROLE_ADMIN,
         * etc). Cannot be null, or contain null values
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         * @see #roles(String...)
         */
        public ArchUser.UserBuilder authorities(String... authorities) {
            return authorities(AuthorityUtils.createAuthorityList(authorities));
        }

        /**
         * Defines if the account is expired or not. Default is false.
         *
         * @param accountExpired true if the account is expired, false otherwise
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        /**
         * Defines if the account is locked or not. Default is false.
         *
         * @param accountLocked true if the account is locked, false otherwise
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        /**
         * Defines if the credentials are expired or not. Default is false.
         *
         * @param credentialsExpired true if the credentials are expired, false otherwise
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        /**
         * Defines if the account is disabled or not. Default is false.
         *
         * @param disabled true if the account is disabled, false otherwise
         * @return the {@link ArchUser.UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public ArchUser.UserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserDetails build() {
            String encodedPassword = this.passwordEncoder.apply(password);
            return new ArchUser(username, encodedPassword, accountId, tenantId, channelType,
                                nickName, avatar,
                                !disabled, !accountExpired,
                                !credentialsExpired, !accountLocked, authorities);
        }
    }


}
