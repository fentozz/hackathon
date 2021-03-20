package org.bars.hackathon.consts;

import lombok.AllArgsConstructor;

/**
 * Пути запросов
 */
@AllArgsConstructor
public class Paths {
    public static final String API_V1 = "/api/v1";

    public static final String API_V2 = "/api/v2";

    public static final String ADMIN_URLS = "/admin/**";

    public static final String SECURE_URLS = "/api/v2/**";

    public static final String[] IGNORED_SECURE_URLS = {
            "/*/*/accounts/login",
            "/*/*/accounts/register",
            "/*/*/accounts/restore",
            "/ws/*",
            "/*/*/version"};
}