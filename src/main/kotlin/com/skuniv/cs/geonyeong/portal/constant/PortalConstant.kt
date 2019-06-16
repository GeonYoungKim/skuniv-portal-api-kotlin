package com.skuniv.cs.geonyeong.portal.constant

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

class PortalConstant {
    companion object {
        const val PROFESSOR_ID_KEY = "professorId"
        const val STUDENT_ID_KEY = "studentId"
        val DAY_OF_WEEK_MAP :BiMap<String, Int> = HashBiMap.create()
        init {
            DAY_OF_WEEK_MAP["일"] = 1
            DAY_OF_WEEK_MAP["월"] = 2
            DAY_OF_WEEK_MAP["화"] = 3
            DAY_OF_WEEK_MAP["수"] = 4
            DAY_OF_WEEK_MAP["목"] = 5
            DAY_OF_WEEK_MAP["금"] = 6
            DAY_OF_WEEK_MAP["토"] = 7
        }
    }
}