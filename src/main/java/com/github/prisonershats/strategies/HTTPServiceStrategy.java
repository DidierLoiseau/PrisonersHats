package com.github.prisonershats.strategies;

import com.github.prisonershats.PrisonersHatsStrategy;
import com.google.gson.Gson;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HTTPServiceStrategy implements PrisonersHatsStrategy<Integer> {

	private String url;

	public HTTPServiceStrategy(String url) {
		this.url = url;
	}

	@Override
	public Integer guessHat(List<Integer> heardHats, List<Integer> visibleHats) throws IOException {

		Gson gson = new Gson();
		RequestJSON requestJSON = new RequestJSON();
		requestJSON.heardHats = ArrayUtils.toPrimitive(heardHats.toArray(new Integer[heardHats.size()]));
		requestJSON.visibleHats = ArrayUtils.toPrimitive(visibleHats.toArray(new Integer[visibleHats.size()]));

		HttpPost request = new HttpPost(url);
		StringEntity params = new StringEntity(gson.toJson(requestJSON));
		request.addHeader("content-type", "application/x-www-form-urlencoded");
		request.setEntity(params);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse httpResponse = httpClient.execute(request);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		ResponseJSON response = gson.fromJson(bufferedReader, ResponseJSON.class);

		return response.guessHat;
	}

    private class RequestJSON {
        private int[] heardHats;
        private int[] visibleHats;
    }

	private class ResponseJSON {
		private int guessHat;
	}

}
