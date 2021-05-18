package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubMembers;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberService;
import org.h2.tools.Csv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

@RestController
@RequestMapping("/csv")
public class CSVController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ClubMembersRepository clubMembersRepository;


    @PostMapping(value = "/upload")
    public String handleCSVUpload(@RequestParam("file") File file) throws Exception{

        var reader = new Scanner(file);
        var fields = new HashMap<String, Integer>();
        var clubcache = new HashMap<String, Club>();
        fields.put("clubname",0);
        fields.put("memberid",1);
        while (reader.hasNextLine()){
            var line =  reader.nextLine().split(",");
            if(line.length!=2){
                return "Bad CSV format";
            }
            var isFieldsLine = line[0].equals("memberid") || line[0].equals("clubname");
            if(isFieldsLine){
                for (int i = 0; i<line.length; i++) {
                    fields.put(line[i],i);
                }
                continue;
            }


            System.out.println("Creating new member "+line[fields.get("memberid")] + " if not existing");
            var mem = memberService.save(new Member(line[fields.get("memberid")]));


            System.out.println("adding new member "+mem.getMemberid()+" to club "+line[fields.get("clubname")]);

            // This requires the club to be present in DB.
            Club club;
            if(clubcache.get(line[fields.get("clubname")])!=null) {
                club = clubcache.get(line[fields.get("clubname")]);
            } else {
                club = clubService.findClubByName(line[fields.get("clubname")]);
                clubcache.put(line[fields.get("clubname")],club);
            }

            clubMembersRepository.save(new ClubMembers(club,mem));


        }


        return "OK";
    }

//    @GetMapping(value = "/download", produces = "text/csv")
//    public ResponseEntity<?> serveCsv() throws Exception{
//        var test = new Csv();
//        test.setFieldDelimiter(',');
//
//        var q = entityManager.createQuery("select ");
//
//
//
//
//        return new ResponseEntity<>()
//
//    }







}
