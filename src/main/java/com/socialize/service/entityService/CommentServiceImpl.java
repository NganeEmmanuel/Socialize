package com.socialize.service.entityService;

import com.socialize.dto.CommentDTO;
import com.socialize.exception.exceptions.CommentNotFoundException;
import com.socialize.exception.exceptions.NoChildCommentFoundException;
import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.model.Comment;
import com.socialize.model.Post;
import com.socialize.repository.CommentRepository;
import com.socialize.repository.PostRepository;
import com.socialize.service.mapperService.CommentMapperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final CommentMapperService commentMapperService;

    /**
     * Returns a list of CommentDTO object based on a specific post
     * @param postId the id of the specific post
     * @param start starting point
     * @param stop ending point
     * @return a list of CommentDTO object based on a specific post
     */
    @Override
    public List<CommentDTO> getComment(Long postId, int start, int stop) throws CommentNotFoundException {

        try {
            Post post = postRepository.findPostById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));


                Pageable pageable = PageRequest.of(start, stop - start);
                List<Comment> comments = commentRepository.findByPost(post,pageable)
                        .orElseThrow(() -> new CommentNotFoundException(postId));

                return comments.stream().map(this::convertToDto).collect(Collectors.toList());


        } catch (CommentNotFoundException ex) {
            logger.error("Error fetching the comments for post with ID: {}", postId, ex);
            throw ex;
        }catch (Exception ex) {
            logger.error("An unexpected error occurred while looking for comments with the post having the id: {}", postId, ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }
        }

    @Override
    public List<CommentDTO> getChildComment(Long postId, Integer parentCommentId, int start, int stop) throws NoChildCommentFoundException {
        try {
            Post post = postRepository.findPostById(postId)
                    .orElseThrow(() -> new PostNotFoundException(postId));

            Pageable pageable = PageRequest.of(start, stop-start);
            List<Comment> comments = commentRepository.findCommentsByPostAndParentCommentId(post,parentCommentId,pageable)
                    .orElseThrow(() -> new NoChildCommentFoundException(postId,parentCommentId));

            return comments.stream().map(this::convertToDto).collect(Collectors.toList());
        } catch (NoChildCommentFoundException ex) {
            logger.error("Error fetching the child comments for post with ID: {} and parent comment with id: {} ", postId,parentCommentId, ex);
            throw ex;
        }catch (Exception ex) {
            logger.error("An unexpected error occurred while looking for comments with the post having the id: {}", postId, ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }
    }

    public CommentDTO convertToDto(Comment post) {
        return commentMapperService.mapToDTO(post);
    }
}
