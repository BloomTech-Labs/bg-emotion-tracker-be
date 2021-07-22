package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Reaction;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "reactionService")
public class ReactionServiceImpl implements ReactionService {

    /**
     * Connects this service to the Reaction table.
     */
    @Autowired
    private ReactionRepository reactionrepos;

    @Autowired
    private MemberService memberService;

    public Reaction findReactionById(long id) throws ResourceNotFoundException {
        return reactionrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction id " + id + " not found!"));
    }

    @Override
    public List<Reaction> findAll() {
        List<Reaction> list = new ArrayList<>();
        reactionrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Reaction save(Reaction reaction) {
        Reaction newReaction = new Reaction();

        if (reaction.getReactionid() != 0) {
            reactionrepos.findById(reaction.getReactionid())
                    .orElseThrow(() -> new ResourceNotFoundException("Reaction id " + reaction.getReactionid() + " not found!"));
            newReaction.setReactionid(reaction.getReactionid());
        }

        newReaction.setReactionvalue(reaction.getReactionvalue());
        newReaction.setReactionint(reaction.getReactionint());

        return reactionrepos.save(newReaction);
    }

    @Override
    public Reaction findReactionByReactionid(long reactionId) {
        return reactionrepos.findReactionByReactionid(reactionId).orElseThrow(() -> new ResourceNotFoundException("Reaction " + reactionId +  " Not Found!"));
    }

}
