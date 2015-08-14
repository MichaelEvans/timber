package timber.lint;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import java.io.InputStream;
import java.util.List;

public class WrongTimberUsageDetectorTest extends LintDetectorTest {
  @Override protected Detector getDetector() {
    return new WrongTimberUsageDetector();
  }

  @Override protected List<Issue> getIssues() {
    return new IssueRegistry().getIssues();
  }

  //this needs to be overridden (see https://code.google.com/p/android/issues/detail?id=182195)
  @Override protected InputStream getTestResource(String relativePath, boolean expectExists) {
    String path = relativePath; //$NON-NLS-1$
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);
    if (!expectExists && stream == null) {
      return null;
    }
    return stream;
  }

  public void testShouldDetectNoWarning() throws Exception {
    assertEquals("",

        lintProject(
            "Sample.java.txt=>Sample.java"
        ));
  }
}
