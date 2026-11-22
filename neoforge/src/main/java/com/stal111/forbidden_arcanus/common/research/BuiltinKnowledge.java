package com.stal111.forbidden_arcanus.common.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltinKnowledge {

    public static final ResourceKeyHelper<Knowledge> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.KNOWLEDGE);

    public static final ResourceKey<Knowledge> WELCOME = HELPER.createKey("welcome");
    public static final ResourceKey<Knowledge> FORBIDDENOMICON = HELPER.createKey("forbiddenomicon");
    public static final ResourceKey<Knowledge> RESEARCH_DESK = HELPER.createKey("research_desk");

}
