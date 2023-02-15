package co.fullstacklabs.cuboid.challenge.controller;

import co.fullstacklabs.cuboid.challenge.dto.CuboidDTO;
import co.fullstacklabs.cuboid.challenge.service.CuboidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */

@RestController
@RequestMapping("/cuboids")
@Validated
public class CuboidController {

    private final CuboidService service;

    @Autowired
    public CuboidController(CuboidService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<CuboidDTO> create(@Valid @RequestBody final CuboidDTO cuboidDTO) {
        CuboidDTO cuboid = service.create(cuboidDTO);
        return new ResponseEntity<>(cuboid, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CuboidDTO> getAll() {
        List<CuboidDTO> cuboidDTOList = service.getAll();

        if (!cuboidDTOList.isEmpty()) {
            cuboidDTOList.forEach(cuboidDTO -> cuboidDTO.setVolume(calculateVolume(cuboidDTO)));
        }

        return cuboidDTOList;
    }

    @PutMapping("{id}")
    public ResponseEntity<CuboidDTO> update(@PathVariable("id") final Long id, @Valid @RequestBody final CuboidDTO cuboidDTO){
        final CuboidDTO cuboid = service.update(id, cuboidDTO);
        return new ResponseEntity<>(cuboid, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity("Object Cuboid not found!", HttpStatus.NO_CONTENT);
    }

    private Double calculateVolume(CuboidDTO cuboidDTO) {
        return (double) (cuboidDTO.getWidth() * cuboidDTO.getHeight() * cuboidDTO.getDepth());
    }

}
