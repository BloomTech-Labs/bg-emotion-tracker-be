package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubMembers;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberService;
import org.apache.tomcat.jni.FileInfo;
import org.h2.tools.Csv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    /**
     * Upload a CSV as a form-data object
     * <br> Example: <a href="http://localhost:2019/csv/upload">http://localhost:2019/csv/upload</a>
     *
     * @param mfile A csv file
     * @throws Exception Exception if a club name is not found it the DB
     */
    @PostMapping(value = "/upload")
    public String handleCSVUpload(@RequestParam("file") MultipartFile mfile) throws Exception{
        var file = mfile.getInputStream();
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


    /**
     * Download all club-member pairs as CSV
     * <br> Example: <a href="http://localhost:2019/csv/download">http://localhost:2019/csv/download</a>
     *
     * @throws Exception Exception if there is any IO issues.
     */
    @GetMapping(value = "/download")
    public StreamingResponseBody serveCsv(HttpServletResponse response) throws Exception{
        var fw = new FileWriter("temp.csv");
        // currently returns all club-members relations in a csv format.
        clubMembersRepository.findAll().forEach(e -> {
            var temp = new StringBuilder();
            temp.append(e.getClub().getClubname()).append(",").append(e.getMember().getMemberid()).append("\n");
            try {
                fw.append(temp);
                System.out.println(temp);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        fw.close();
        var file = new File("temp.csv");

        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"members.csv\"");

        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[4096];
            InputStream inputStream = new FileInputStream(file);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        };

    }







}
