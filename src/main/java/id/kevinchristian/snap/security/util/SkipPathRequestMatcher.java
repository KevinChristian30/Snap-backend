package id.kevinchristian.snap.security.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.stream.Collectors;

public class SkipPathRequestMatcher implements RequestMatcher {
    private OrRequestMatcher skipMatcher;
    private OrRequestMatcher processingMatcher;

    public SkipPathRequestMatcher(List<String> pathToSkip, List<String> pathToProcess) {
        List<RequestMatcher> pathToSkipRequestMatchers =
                pathToSkip.stream().map(p -> new AntPathRequestMatcher(p)).collect(Collectors.toList());
        skipMatcher = new OrRequestMatcher(pathToSkipRequestMatchers);

        List<RequestMatcher> pathToProcessRequestMatchers =
                pathToProcess.stream().map(p -> new AntPathRequestMatcher(p)).collect(Collectors.toList());
        processingMatcher = new OrRequestMatcher(pathToProcessRequestMatchers);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (skipMatcher.matches(request)) {
            return false;
        }

        return processingMatcher.matches(request);
    }
}
