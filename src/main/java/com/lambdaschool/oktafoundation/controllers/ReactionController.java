package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Activity;
import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.models.Reaction;
import com.lambdaschool.oktafoundation.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reactions")
public class ReactionController
{
    @Autowired
    private ReactionService reactionService;


    @GetMapping(value = "/reactions",
        produces = "application/json")
    public ResponseEntity<?> getAllReactions()
    {
        List<Reaction> allReactions = reactionService.findAll();
        return new ResponseEntity<>(allReactions, HttpStatus.OK);
    }
}
