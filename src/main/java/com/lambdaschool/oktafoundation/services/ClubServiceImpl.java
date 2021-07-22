package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.*;
import com.lambdaschool.oktafoundation.views.ClubsCheckInOutSummary;
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

    @Autowired
    private MemberReactionRepository memberReactionRepos;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepos;

    @Override
    public List<Club> findAll() {
        List<Club> clubList = new ArrayList<>();

        clubrepos.findAll()
                .iterator()
                .forEachRemaining(clubList::add);
        clubList.sort((c1,c2)-> c1.getClubname().compareToIgnoreCase(c2.getClubname()));
        return clubList;
    }

    @Override
    public Club findClubById(Long clubid) throws
            ResourceNotFoundException
    {
        return clubrepos.findClubByClubid(clubid)
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
            if(ca.getActivity().getActivityid() != 0) {
                activityRepos.findById(ca.getActivity().getActivityid()).orElseThrow();
                newActivity.setActivityid(ca.getActivity().getActivityid());
            }
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
                        .orElseThrow(() -> new ResourceNotFoundException("User id " + cu.getUser().getUserid() + " not found!"));
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
            newClub.getUsers().add(new ClubUsers(newClub, newUser));
        }

        return clubrepos.save(newClub);
    }

    @Override
    public Club update(Club club, long clubid) {

        Club updateClub = clubrepos.findById(clubid)
                .orElseThrow(() -> new ResourceNotFoundException("Club" + clubid + "not found."));

//      set Fields
        if (club.getClubname() != null)
        {
            updateClub.setClubname(club.getClubname());
        }
//      set relationships
        if(club.getActivities()
                .size() > 0)
        {
            updateClub.getActivities()
                    .clear();
            for(ClubActivities ca: club.getActivities())
            {
                Activity newActivity = activityRepos.findById(ca.getActivity().getActivityid())
                        .orElseThrow(() -> new EntityNotFoundException("Activity" + ca.getActivity().getActivityid() + "not found."));

                updateClub.getActivities().add(new ClubActivities(updateClub, newActivity));
            }
        }
        if(club.getUsers()
                .size()>0)
        {
            updateClub.getUsers()
                    .clear();
            for(ClubUsers cu: club.getUsers())
            {
                User addUser = userRepository.findById(cu.getUser().getUserid())
                        .orElseThrow(() -> new ResourceNotFoundException("User id" + cu.getUser().getUserid() + "not found."));

                updateClub.getUsers().add(new ClubUsers(updateClub,addUser));
            }
        }
        return clubrepos.save(updateClub);
    }


    @Override
    public void delete(long clubid) {
        clubrepos.findById(clubid)
                .orElseThrow(() -> new ResourceNotFoundException("Club id" + clubid + "not found!"));
        clubrepos.deleteById(clubid);
    }


    @Transactional
    @Override
    public void deleteAll()
    {
        clubrepos.deleteAll();
    }


    @Override
    public Club findClubByName(String name){
        return clubrepos.findClubByClubname(name).orElseThrow(() -> new ResourceNotFoundException("Club with name " + name + " not found!"));
    }

    @Override
    public List<ClubsCheckInOutSummary> getClubsCheckInOutSummary() {
        return clubrepos.getClubsCheckInOutSummary();
    }

}
