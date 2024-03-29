package com.kill4us.shortlink.admin.controller;

import com.kill4us.shortlink.admin.common.convention.result.Result;
import com.kill4us.shortlink.admin.common.convention.result.Results;
import com.kill4us.shortlink.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.kill4us.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.kill4us.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.kill4us.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接分组控制层
 */
@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接分组
     * @param requestParam 分组名
     * @return
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> saveGroup(@RequestBody ShortLinkGroupSaveReqDTO requestParam) {
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }

    @GetMapping("/api/short-link/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    @PutMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam) {
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    @DeleteMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestParam String gid) {
        groupService.deleteGroup(gid);
        return Results.success();
    }

}
