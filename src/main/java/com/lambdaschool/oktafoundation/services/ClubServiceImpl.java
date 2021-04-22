package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ClubActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.ClubUsersRepository;
import com.lambdaschool.oktafoundation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "clubService")
public class ClubServiceImpl implements ClubService{

    @Autowired
    private ClubRepository clubrepos;

    @Autowired
    private ClubActivityRepository clubactivityrepos;

    @Autowired
    private ClubUsersRepository clubUsersrepo;

    @Autowired
    private RoleRepository rolerepos;

    @Autowired
    private RoleService roleService;

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

    @Transactional
    @Override
    public Club save(Club club) {

        Club newClub = new Club();

        if(club.getClubid() != 0)
        {
            clubrepos.findById(club.getClubid())
                    .orElseThrow(() -> new ResourceNotFoundException("Club id" + club.getClubid() + "not found!"));
            newClub.setClubid(club.getClubid());
        }
//      set Fields
        newClub.setClubname(club.getClubname());
//      set relationships
        newClub.getActivities()
                .clear();
        for (ClubActivities ca: club.getActivities())
        {
            Activity newActivity = new Activity();
            newActivity.setActivityname(ca.getActivity().getActivityname());

            ClubActivities newclubActivities = new ClubActivities();
            newclubActivities.setActivity(newActivity);
            newclubActivities.setClub(newClub);
            newclubActivities.getReactions()
                    .clear();
            for (MemberReactions mr: ca.getReactions())
            {
                Member newMember = new Member();
                newMember.setMemberid(mr.getMember().getMemberid());

                Reaction newReaction = new Reaction();
                newReaction.setReactionvalue(mr.getReaction().getReactionvalue());

                MemberReactions newMemberReaction = new MemberReactions();
                newMemberReaction.setMember(newMember);
                newMemberReaction.setReaction(newReaction);
                newMemberReaction.setCheckedin(mr.getCheckedin());
                newMemberReaction.setClubactivity(newclubActivities);

                newclubActivities.getReactions().add(newMemberReaction);
            }

            newClub.getActivities().add(newclubActivities);
        }

        newClub.getUsers()
                .clear();
        for (ClubUsers cu: club.getUsers())
        {
            User newUser = new User();

            if (cu.getUser().getUserid() != 0)
            {
                clubUsersrepo.findById(cu.getUser().getUserid())
                        .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
                newUser.setUserid(cu.getUser().getUserid());
            }

            newUser.setUsername(cu.getUser().getUsername()
                    .toLowerCase());

            newUser.getRoles()
                    .clear();
            for (UserRoles ur : cu.getUser().getRoles())
            {
                Role addRole = roleService.findRoleById(ur.getRole()
                        .getRoleid());
                newUser.getRoles()
                        .add(new UserRoles(newUser,
                                addRole));
            }

            newUser.getUseremails()
                    .clear();
            for (Useremail ue : cu.getUser().getUseremails())
            {
                newUser.getUseremails()
                        .add(new Useremail(newUser,
                                ue.getUseremail()));
            }
            newClub.getUsers().add(new ClubUsers(newUser, newClub));
        }

        return clubrepos.save(newClub);
    }

    @Override
    public void update(Club club, long clubid) {

    }

    //    @Transactional
//    @Override
//    public void update(Club club, long clubid) {
//
//        Club updateClub = clubrepos.findById(clubid)
//                .orElseThrow(() -> new EntityNotFoundException("Club Id" + clubid + "not found."));
//
//        if(club.getClubname() != null)
//        {
//            updateClub.setClubname(club.getClubname().toLowerCase());
//        }
//        if(club.getActivities().size() > 0)
//        {
//            updateClub.getActivities().clear();
//            for(ClubActivities ca: club.getActivities())
//            {
//                ClubActivities newClubActivities = clubactivityrepos.findById(ca.get())
//                        .orElseThrow(() -> new EntityNotFoundException("Club Activity" + ca.getClubactivityid() + "Not found!"));
//                updateClub.getActivities().add(newClubActivities);
//            }
//        }
//        if(club.getUsers().size() > 0)
//        {
//            updateClub.getUsers().clear();
//            for(ClubUsers cu: club.getUsers())
//            {
//                ClubUsers newClubUser = clubUsersrepo.findById(cu.getUser().getUserid())
//                        .orElseThrow(() -> new EntityNotFoundException("Club User" + cu.getUser().getUserid() + "Not found!"));
//                updateClub.getUsers().add(newClubUser);
//            }
//        }
//    }

    @Override
    public void delete(long clubid) {
        clubrepos.findById(clubid)
                .orElseThrow(() -> new ResourceNotFoundException("Club id" + clubid + "not found!"));
        clubrepos.deleteById(clubid);
    }
}
