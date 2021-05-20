package cn.edu.szu.algo.controller;

import cn.edu.szu.algo.service.IAlgoService;
import cn.edu.szu.entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RestController
public class AlgoDataController {

    private final IAlgoService algoService;

    @Autowired
    public AlgoDataController(IAlgoService trajectoryService) {
        this.algoService = trajectoryService;
    }

    @GetMapping("/trajectory")
    public ResponseEntity getTrajectory(
            @RequestParam(name = "startTime", defaultValue = "2020/08/01 00:00:00", required = false) String startTime,
            @RequestParam(name = "endTime", defaultValue = "2020/08/02 00:00:00", required = false) String endTime) {
        return new ResponseEntity()
                .setSuccess(true)
                .setMessage("查询成功")
                .setData(algoService.getAllTravelTrajectory(startTime, endTime));
    }

    @GetMapping("/density")
    public ResponseEntity getDensity(
            @RequestParam(name = "startTime", defaultValue = "2020/08/01 00:00:00", required = false) String startTime,
            @RequestParam(name = "endTime", defaultValue = "2020/08/02 00:00:00", required = false) String endTime) {
        return new ResponseEntity()
                .setSuccess(true)
                .setMessage("查询成功")
                .setData(algoService.getDensity(startTime, endTime));
    }

    @GetMapping("/stagnation")
    public ResponseEntity getStagnation(
            @RequestParam(name = "startTime", defaultValue = "2020/08/01 00:00:00", required = false) String startTime,
            @RequestParam(name = "endTime", defaultValue = "2020/08/02 00:00:00", required = false) String endTime) {
        return new ResponseEntity()
                .setSuccess(true)
                .setMessage("查询成功")
                .setData(algoService.getStagnation(startTime, endTime));
    }

    @GetMapping("/poi")
    public ResponseEntity getPoi() {
        return new ResponseEntity()
                .setSuccess(true)
                .setMessage("查询成功")
                .setData(algoService.getPoi());
    }
}
