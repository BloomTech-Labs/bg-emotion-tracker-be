package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Member;
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


    @PostMapping(value = "/upload")
    public String handleCSVUpload(@RequestParam("file") File file) throws Exception{

        var reader = new Scanner(file);
        var fields = new HashMap<String, Integer>();
//        var clubcache = new HashMap<String, Club>();
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
            // We currently do not have club-member relationship, this is going to be confirmed with stakeholders. We're just adding new members to DB.
            System.out.println("adding new member "+line[fields.get("memberid")]);
            memberService.save(new Member(line[fields.get("memberid")]));

//            System.out.println("adding new member"+line[fields.get("memberid")]+" to club "+line[fields.get("clubid")]);

//            var mem

//            Club club;
//            if(clubcache.get(line[fields.get("clubname")])!=null) {
//                club = clubcache.get(line[fields.get("clubname")]);
//            } else {
//                club = clubService.findClubByName(line[fields.get("clubname")]);
//            }

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
