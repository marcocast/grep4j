package org.grep4j.core.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

/**
 * Model class containing details of the server where the target file is stored.
 *
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 */
@EqualsAndHashCode
@ToString
public class ServerDetails {

    private static final List<String> LOCALHOST_ALIASES = List.of("localhost", "127.0.0.1");

    @Getter
    private final String host;

    @Getter
    @Setter
    private String user;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Integer port;

    @Getter
    @Setter
    private String privateKeyLocation;

    @Getter
    @Setter
    private boolean isPasswordRequired;

    /**
     * @param host hostname or IP of the server. Use {@code "localhost"} or {@code "127.0.0.1"} for local.
     */
    public ServerDetails(String host) {
        this.host = host;
        this.port = 22;
    }

    /** @return {@code true} if this represents the local machine */
    public boolean isLocalhost() {
        return LOCALHOST_ALIASES.contains(host.toLowerCase());
    }

    public void validate() {
        if (StringUtils.isBlank(host)) {
            throw new IllegalArgumentException("Host is empty or null");
        }
        if (!isLocalhost()) {
            if (StringUtils.isBlank(user)) {
                throw new IllegalArgumentException("User is empty or null");
            }
            if (isPasswordRequired()) {
                String errorComponent = StringUtils.isNotBlank(privateKeyLocation) ? "Passphrase" : "Password";
                if (StringUtils.isBlank(password)) {
                    throw new IllegalArgumentException(errorComponent + " is empty or null");
                }
            }
            if (port < 1 || port > 65535) {
                throw new IllegalArgumentException("Port out of possible ranges (1 to 65535)");
            }
        }
    }
}
