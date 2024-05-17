package com.socialize.service.mapperService;

import com.socialize.dto.ReactionDTO;
import com.socialize.model.Reaction;

public class ReactionMapperService implements MapperService<Reaction, ReactionDTO> {

    @Override
    public Reaction mapToEntity(ReactionDTO reactionDTO) {
        Reaction reaction = new Reaction();
        reaction.setReaction(reactionDTO.getReaction());
        reaction.setUser(new UserMapperService().mapToEntity(reactionDTO.getUser()));
        reaction.setPost(new PostMapperService().mapToEntity(reactionDTO.getPost()));
        reaction.setComment(new CommentMapperService().mapToEntity(reactionDTO.getComment()));
        // Set additional fields as needed
        return reaction;
    }

    @Override
    public ReactionDTO mapToDTO(Reaction reaction) {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setId(reaction.getId());
        reactionDTO.setReaction(reaction.getReaction());
        reactionDTO.setUser(new UserMapperService().mapToDTO(reaction.getUser()));
        reactionDTO.setPost(new PostMapperService().mapToDTO(reaction.getPost()));
        reactionDTO.setComment(new CommentMapperService().mapToDTO(reaction.getComment()));
        // Set additional fields as needed
        return reactionDTO;
    }
}
