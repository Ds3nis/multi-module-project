package ag.selm.catalog.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class BadRequestControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException bindException, Locale locale){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("errors.400.title", new Object[0], "erors.400.title"
                        ,locale));
        problemDetail.setProperty("errors", bindException.getAllErrors().stream().
                map(objectError -> objectError.getDefaultMessage()).toList());
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
