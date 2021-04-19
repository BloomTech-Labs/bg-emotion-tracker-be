package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.ClubActivity;
import com.lambdaschool.oktafoundation.repository.ClubActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "clubActivityService")
public class ClubActivityServiceImpl implements ClubActivityService{

    @Autowired
    private ClubActivityRepository clubactivityrepos;

    @Override
    public List<ClubActivity> findAll() {
        List<ClubActivity> clubActivitiesList = new ArrayList<>();

        clubactivityrepos.findAll()
                .iterator()
                .forEachRemaining(clubActivitiesList::add);
        return clubActivitiesList;
    }

    @Override
    public ClubActivity findClubActivityById(Long clubactivityid) throws ResourceNotFoundException
    {
        return clubactivityrepos.findById(clubactivityid)
                .orElseThrow(() -> new ResourceNotFoundException("Club Acitivty id" + clubactivityid + "not found!"));
    }
}
