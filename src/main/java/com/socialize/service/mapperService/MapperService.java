package com.socialize.service.mapperService;

/**
 * Interface for mapping between entity and DTO.
 * @param <T> The type of the entity.
 * @param <U> The type of the DTO.
 */
public interface MapperService<T, U> {

    /**
     * Maps a DTO to an entity.
     *
     * @param dto The DTO object to be mapped.
     * @return The mapped entity object.
     */
    T mapToEntity(U dto);

    /**
     * Maps an entity to a DTO.
     *
     * @param entity The entity object to be mapped.
     * @return The mapped DTO object.
     */
    U mapToDTO(T entity);
}
