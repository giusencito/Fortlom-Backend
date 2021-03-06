package upc.edu.pe.FortlomBackend.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import upc.edu.pe.FortlomBackend.backend.domain.model.entity.Report;
import upc.edu.pe.FortlomBackend.backend.domain.model.entity.User;
import upc.edu.pe.FortlomBackend.backend.domain.persistence.ReportRepository;
import upc.edu.pe.FortlomBackend.backend.domain.persistence.UserRepository;
import upc.edu.pe.FortlomBackend.backend.domain.service.ReportInterface;
import upc.edu.pe.FortlomBackend.shared.exception.ResourceNotFoundException;
import upc.edu.pe.FortlomBackend.shared.exception.ResourceValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ReportServiceImpl implements ReportInterface {

    private static final String ENTITY = "Report";
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final Validator validator;


    public ReportServiceImpl( ReportRepository reportRepository, UserRepository userRepository, Validator validator){

        this.reportRepository=reportRepository;
        this.userRepository=userRepository;
        this.validator=validator;


    }
    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public Page<Report> getAll(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }
    @Override
    public Report getById(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, reportId));
    }
    @Override
    public Report create(Long UserMainId, Long UserReportedId, Report request) {
        User usermain = userRepository.findById(UserMainId)
                .orElseThrow(() -> new ResourceNotFoundException("User", UserMainId));
        return userRepository.findById(UserReportedId)
                .map(report -> {
                    request.setUserReported(report);
                    request.setUserMain(usermain);
                    return reportRepository.save(request);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User", UserReportedId));

    }

    @Override
    public List<Report> findByUserMainId(Long UserMainId) {
        return reportRepository.findByUserMainId(UserMainId);
    }
    @Override
    public List<Report> findByUserReportedId(Long UserReportedId) {
        return reportRepository.findByUserReportedId(UserReportedId);
    }
    @Override
    public ResponseEntity<?> delete(Long reportId) {
        return reportRepository.findById(reportId).map(post -> {
            reportRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, reportId));
    }


    @Override
    public Report update(Long reportId, Report request) {
        Set<ConstraintViolation<Report>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return reportRepository.findById(reportId).map(event ->
                reportRepository.save(
                        event
                                .withReportDescription(request.getReportDescription())

                )

        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, reportId));
    }







}
