package timber.lint;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

public class TimberLintTest extends LintDetectorTest {

  @Override protected Detector getDetector() {
    return new WrongTimberUsageDetector();
  }

  @Override protected List<Issue> getIssues() {
    return new IssueRegistry().getIssues();
  }

  @Override protected InputStream getTestResource(String relativePath, boolean expectExists) {
    String path = "data" + File.separator + relativePath;
    InputStream stream = TimberLintTest.class.getResourceAsStream(path);
    if (stream == null) {
      File root = getRootDir();
      assertNotNull(root);
      String pkg = TimberLintTest.class.getName();
      pkg = pkg.substring(0, pkg.lastIndexOf('.'));
      File f = new File(root,
          "tools/base/lint/libs/lint-tests/src/test/java/".replace('/', File.separatorChar)
              + pkg.replace('.', File.separatorChar)
              + File.separatorChar + path);
      if (f.exists()) {
        try {
          return new BufferedInputStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
          stream = null;
          if (expectExists) {
            fail("Could not find file " + relativePath);
          }
        }
      }
    }
    if (!expectExists && stream == null) {
      return null;
    }
    return stream;
  }

  public void testLogTagLength() throws Exception {
    assertEquals(
        "",
        lintProject(
            "src/test/pkg/LogTest.java.txt=>src/test/pkg/LogTest.java",
            // stub for type resolution
            "src/test/pkg/Log.java.txt=>src/android/util/Log.java"
        )
    );

  }
}
