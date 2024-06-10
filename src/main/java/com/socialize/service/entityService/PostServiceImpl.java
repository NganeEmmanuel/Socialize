package com.socialize.service.entityService;

import com.socialize.dto.PostDTO;
import com.socialize.exception.exceptions.PostNotFoundException;
import com.socialize.model.Post;
import com.socialize.repository.PostRepository;
import com.socialize.service.mapperService.PostMapperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final PostMapperService postMapperService;

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to delete.
     * @throws PostNotFoundException if the post with the given ID is not found.
     */
    @Override
    public void deletePost(Long id) throws PostNotFoundException {
        try {
            Post post = getPostById(id);
            postRepository.delete(post);
            logger.info("Post with ID: {} deleted successfully", id);
        } catch (PostNotFoundException ex) {
            logger.error("Post not found with ID: {}", id, ex);
            throw ex; // rethrow the exception to be handled by the global exception handler
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while deleting post with ID: {}", id, ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return The retrieved post.
     * @throws PostNotFoundException if the post with the given ID is not found.
     */
    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> {
            logger.error("Post not found with ID: {}", postId);
            return new PostNotFoundException(postId);
        });
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId The ID of the post to retrieve.
     * @return The retrieved post converted to postDTO.
     * @throws PostNotFoundException if the post with the given ID is not found.
     */
    @Override
    public PostDTO getPostDTOById(Long postId) throws PostNotFoundException {
        try{
            Post post = getPostById(postId);
            return postMapperService.mapToDTO(post);
        } catch (PostNotFoundException ex) {
            logger.error("Post not found with ID: {}", postId, ex);
            throw ex; // rethrow the exception to be handled by the global exception handler
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while getting post with ID: {}", postId, ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }
    }

    @Override
    public void updatePost(Long postId, PostDTO postDTO) throws PostNotFoundException {
        try {
            Post existingPost = getPostById(postId);

            // Update fields of the existing post with values from postDTO
            existingPost.setContent(postDTO.getContent());
            existingPost.setMediaFileContent(postDTO.getMediaContent());
            existingPost.setMediaFileName(postDTO.getMediaName());
            existingPost.setMediaMimeType(postDTO.getMediaType());
            existingPost.setTotalReactions(postDTO.getTotalReactions());
            existingPost.setTotalComments(postDTO.getTotalComments());

            postRepository.save(existingPost);
            logger.info("Post with ID: {} updated successfully", postId);
        } catch (PostNotFoundException ex) {
            logger.error("Post not found with ID: {}", postId, ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while updating post with ID: {}", postId, ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        try{
           Post post = postMapperService.mapToEntity(postDTO);
           Post savedPost = postRepository.save(post);
           return postMapperService.mapToDTO(savedPost);
        }catch(Exception ex){
            logger.error("error creating a post", ex);
            throw  new RuntimeException("an error occurred while creating  a post", ex);
        }
    }
}
