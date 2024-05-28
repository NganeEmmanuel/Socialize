package com.socialize.controller;

import com.socialize.dto.UserDTO;
import com.socialize.exception.exceptions.NoMatchingUserFoundException;
import com.socialize.service.entityService.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private SearchServiceImpl searchService;

    /**
     * Get a list of users by their username or name
     * @param keyword the keyword to search for
     * @param start the start index of the result set to return
     * @param stop the stop index of the result set to return
     * @return a list of UserDTOs matching the provided keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getUserByUsernameOrName(@RequestParam String keyword, @RequestParam int start,
                                                                 @RequestParam int stop) {
        try{
            List<UserDTO> feed = searchService.getUserDTOByUsername(keyword,start,stop);

            return ResponseEntity.ok(feed);
        }catch (NoMatchingUserFoundException ex){
            logger.error("No user found with username and/or name matching : {}", keyword, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception ex){
            logger.error("An unexpected error occurred while looking for a user with the provided search keyword : {}", keyword, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
