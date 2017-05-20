package fi.thakki.depemp.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class FrontPageTest extends WebTestBase {

    @Override
    protected void onSetUpComplete() {
        // Nothing.
    }

    @Test
    public void testFrontPage() {
        webDriver().get(baseUrl());
        assertThat(webDriver().getTitle()).isEqualTo("Depemp exercise project");
        assertThat(webDriver().getPageSource()).contains("Choose your destiny");
    }
}
