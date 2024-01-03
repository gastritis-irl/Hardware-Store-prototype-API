package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.dto.HardwarePartPageDTO;
import edu.bbte.idde.bfim2114.springbackend.mapper.HardwarePartMapper;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.repository.HardwareRepository;
import edu.bbte.idde.bfim2114.springbackend.util.SpecificationFields;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    static final int PAGE_SIZE = 12;
    private final HardwareRepository hardwareRepository;
    private final UserService userService;
    private final HardwarePartMapper hardwarePartMapper;

    @Override
    public Optional<HardwarePart> findById(Long partId) {

        return hardwareRepository.findById(partId);
    }

    @Override
    public boolean isValid(HardwarePart part) {
        if (part == null) {
            log.error("HardwarePart is null so it is invalid");
            return true;
        }
        Long userId = part.getUser().getId();

        return !userService.existsById(userId) || part.getName() == null
            || part.getName().isEmpty();
    }

    @Override
    public HardwarePart create(HardwarePart part) {
        if (isValid(part)) {
            log.error("Invalid HardwarePart");
            throw new IllegalArgumentException("Invalid HardwarePart");
        }
        userService.addHardwarePart(part.getUser(), part);
        log.info("Creating HardwarePart: {}", part);
        return hardwareRepository.save(part);
    }

    @Transactional
    @Override
    public void delete(Long partId) {

        log.info("Deleting HardwarePart by id: {}", partId);
        Optional<HardwarePart> part = hardwareRepository.findById(partId);
        part.ifPresent(hardwarePart -> userService.removeHardwarePart(hardwarePart.getUser(), hardwarePart));
        hardwareRepository.deleteById(partId);
    }

    @Transactional
    @Override
    public HardwarePart update(HardwarePart part) {
        if (isValid(part)) {
            log.error("Invalid HardwarePart");
            throw new IllegalArgumentException("Invalid HardwarePart");
        }
        userService.addHardwarePart(part.getUser(), part);
        log.info("Updating HardwarePart: {}", part);
        return hardwareRepository.save(part);
    }

    @Override
    public List<HardwarePart> findAll() {

        log.info("Finding all HardwareParts");
        return hardwareRepository.findAll();
    }

    @Override
    public HardwarePartPageDTO findAllWithFilters(SpecificationFields fields) {
        Specification<HardwarePart> specification = fields.getSpec();
        specification = applyUserFilter(fields.getUserId(), specification);
        specification = applyTextSearchFilter(fields.getTextSearch(), specification);
        specification = applyPriceFilter(fields.getMinPrice(), fields.getMaxPrice(), specification);
        specification = applyCategoryFilter(fields.getCategoryName(), specification);

        Pageable pageable = createPageable(fields.getPage(), fields.getSortBy(), fields.getDirection());
        Page<HardwarePart> hardwarePartPage = hardwareRepository.findAll(specification, pageable);

        return hardwarePartMapper.hardwarePartPageToDTO(
            hardwarePartPage.getContent(),
            hardwarePartPage.getTotalPages(),
            hardwarePartPage.getNumberOfElements());
    }

    private Specification<HardwarePart> applyCategoryFilter(String categoryName,
                                                            Specification<HardwarePart> spec) {
        if (categoryName != null) {
            return filterByCategoryName(categoryName, spec);
        }
        return spec;
    }

    private Specification<HardwarePart> applyUserFilter(Long userId, Specification<HardwarePart> spec) {
        if (userId != null && userId != -1 && userId != 0) {
            return filterByUserId(userId, spec);
        }
        return spec;
    }

    private Specification<HardwarePart> applyTextSearchFilter(String textSearch, Specification<HardwarePart> spec) {
        if (textSearch != null) {
            return filterByTextInNameOrDescription(textSearch, spec);
        }
        return spec;
    }

    private Specification<HardwarePart> applyPriceFilter(Double minPrice,
                                                         Double maxPrice, Specification<HardwarePart> spec) {
        if (minPrice != null || maxPrice != null) {
            return filterByPrice(minPrice, maxPrice, spec);
        }
        return spec;
    }

    private Pageable createPageable(int page, String sortBy, String direction) {
        String sortByField = Optional.ofNullable(sortBy).orElse("id");
        String currentDirection = Optional.ofNullable(direction).orElse("ASC");

        Sort sort = currentDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortByField).ascending() : Sort.by(sortByField).descending();
        return PageRequest.of(Math.max(page - 1, 0), PAGE_SIZE, sort);
    }

    private Specification<HardwarePart> filterByCategoryName(String categoryName,
                                                             Specification<HardwarePart> specification) {
        Specification<HardwarePart> spec = specification;
        if (categoryName != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("name"), categoryName));
        }
        return spec;
    }

    private Specification<HardwarePart> filterByPrice(Double minPrice, Double maxPrice,
                                                      Specification<HardwarePart> specification) {
        Specification<HardwarePart> spec = specification;
        if (minPrice != null && minPrice != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null && maxPrice != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        return spec;
    }

    private Specification<HardwarePart> filterByTextInNameOrDescription(String textSearch,
                                                                        Specification<HardwarePart> specification) {
        Specification<HardwarePart> spec = specification;
        if (textSearch != null) {
            String textSearchLower = "%" + textSearch.toLowerCase(Locale.US) + "%";
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), textSearchLower),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), textSearchLower)
                )
            );
        }
        return spec;
    }


    private Specification<HardwarePart> filterByUserId(Long userId, Specification<HardwarePart> specification) {
        Specification<HardwarePart> spec = specification;
        if (userId != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId)
            );
        }
        return spec;
    }
}
