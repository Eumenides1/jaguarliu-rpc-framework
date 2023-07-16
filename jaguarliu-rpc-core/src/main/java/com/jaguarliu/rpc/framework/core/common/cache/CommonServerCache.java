package com.jaguarliu.rpc.framework.core.common.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.jaguarliu.rpc.framework.core.registy.URL;

public class CommonServerCache {
    public static final Map<String,Object> PROVIDER_CLASS_MAP = new HashMap<>();
    public static final Set<URL> PROVIDER_URL_SET = new HashSet<>();
}
