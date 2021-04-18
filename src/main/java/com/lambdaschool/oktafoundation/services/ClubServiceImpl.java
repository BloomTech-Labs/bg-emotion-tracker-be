package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "clubService")
public class ClubServiceImpl implements ClubService{

    @Autowired
    private ClubRepository clubrepos;

    @Override
    public List<Club> findAll() {
        List<Club> clubList = new ArrayList<>();

        clubrepos.findAll()
                .iterator()
                .forEachRemaining(clubList::add);
        return clubList;
    }

    @Override
    public Club findClubById(Long clubid) throws
            ResourceNotFoundException
    {
        return clubrepos.findById(clubid)
            .orElseThrow(() -> new ResourceNotFoundException("Club id" + clubid + "not found!"));
    }

    @Override
    public Club save(Club newClub) {
        return null;
    }

    @Override
    public void update(Club updateClub, long clubid) {

    }

    @Override
    public void delete(long clubid) {
        clubrepos.findById(clubid)
                .orElseThrow(() -> new ResourceNotFoundException("Club id" + clubid + "not found!"));
        clubrepos.deleteById(clubid);
    }
}
