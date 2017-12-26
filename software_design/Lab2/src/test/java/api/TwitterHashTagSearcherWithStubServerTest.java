package api;

import com.xebialabs.restito.server.StubServer;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;

/**
 * Created by nikita on 07.10.16.
 */
public class TwitterHashTagSearcherWithStubServerTest {
    private final TwitterHashTagSearcher searcher = new TwitterHashTagSearcher();

    @Test
    public void testGetJSONbyQuery() {
        int port = 32453;
        withStubServer(port, s -> {
            String entryPoint = "http://localhost:" + s.getPort();
            URLSearchQuery query = new URLSearchQuery(entryPoint);
            whenHttp(s)
                    .match()
                    .then(stringContent("test"));
            String result = searcher.getStringByQuery(query);
            Assert.assertEquals("test\n", result);
        });
    }


    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}