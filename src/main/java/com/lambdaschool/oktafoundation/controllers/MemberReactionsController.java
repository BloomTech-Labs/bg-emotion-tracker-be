package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/memberreactions")
public class MemberReactionsController {

    @Autowired
    private MemberReactionService memberReactionService;

    @GetMapping(value = "/memberreactions",
    produces = "application/json")
    public ResponseEntity<?> listAllMemberReactions()
    {
        List<MemberReactions> allMemberReactions = memberReactionService.findAll();
        return new ResponseEntity<>(allMemberReactions, HttpStatus.OK);
    }
    @GetMapping(value = "/memberreaction/{id}",
    produces = "application/json")
    public ResponseEntity<?> getMemberReactionById(@PathVariable Long id)
    {
        MemberReactions mr = memberReactionService.findMemberReactionById(id);
        return new ResponseEntity<>(mr, HttpStatus.OK);
    }
}
