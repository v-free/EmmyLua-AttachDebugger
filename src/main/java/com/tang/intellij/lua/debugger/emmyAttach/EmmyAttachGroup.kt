/*
 * Copyright (c) 2017. tangzx(love.tangzx@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.tang.intellij.lua.debugger.emmyAttach

import com.intellij.execution.process.ProcessInfo
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolder
import com.intellij.xdebugger.attach.XAttachProcessPresentationGroup
import com.tang.intellij.lua.debugger.utils.getDisplayName
import com.tang.intellij.lua.lang.LuaIcons
import java.io.File
import javax.swing.Icon
import javax.swing.filechooser.FileSystemView

class EmmyAttachGroup : XAttachProcessPresentationGroup {

    companion object {
        val instance = EmmyAttachGroup()
    }

    override fun getItemDisplayText(project: Project, processInfo: ProcessInfo, userDataHolder: UserDataHolder): String {
        val map = userDataHolder.getUserData(EmmyAttachDebuggerProvider.DETAIL_KEY)
        if (map != null) {
            val detail = map[processInfo.pid]
            if (detail != null)
                return getDisplayName(processInfo, detail)
        }
        return processInfo.executableName
    }

    override fun getItemIcon(project: Project, processInfo: ProcessInfo, userDataHolder: UserDataHolder): Icon {
        val map = userDataHolder.getUserData(EmmyAttachDebuggerProvider.DETAIL_KEY)
        if (map != null) {
            val detail = map[processInfo.pid]
            if (detail != null) {
                val file = File(detail.path)
                if (file.exists()) {
                    val sf = FileSystemView.getFileSystemView()
                    return sf.getSystemIcon(file)
                }
            }
        }
        return LuaIcons.FILE
    }

    override fun getGroupName() = "EmmyLua Attach Debugger"

    override fun compare(a: ProcessInfo, b: ProcessInfo): Int =
            a.executableName.lowercase().compareTo(b.executableName.lowercase())

    override fun getOrder(): Int {
        return 0
    }

    override fun getProcessIcon(p0: Project, p1: ProcessInfo, p2: UserDataHolder): Icon {
        return getItemIcon(p0,p1,p2);
    }
    override fun getProcessDisplayText(p0: Project, p1: ProcessInfo, p2: UserDataHolder): String {
        return getItemDisplayText(p0,p1,p2);
    }
}