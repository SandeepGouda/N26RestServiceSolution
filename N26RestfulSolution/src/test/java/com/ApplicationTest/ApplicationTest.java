package com.ApplicationTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.Application;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

/**
 * Application Tester.
 */
@RunWith(HierarchicalContextRunner.class)
public class ApplicationTest {

    public class GivenApplication {

        public class WhenApplicationStarts {

            @Test
            public void thenApplicationShouldRun() {
                Application.main(new String[] {});
            }
        }
    }
}