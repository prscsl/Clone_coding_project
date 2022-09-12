package com.sparta.clone.service;

import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.repository.CrawRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrawService {

    private final CrawRepository crawRepository;


    private static String CGV_MOVIE_URL = "http://www.cgv.co.kr/movies/";

    @PostConstruct
    public void getCGVMovie() throws IOException {


        Document doc = Jsoup.connect(CGV_MOVIE_URL).get();
        Elements ranks = doc.select(".rank");
        Elements imgs = doc.select(".thumb-image > img");
        Elements dtail = doc.select("div.box-image > a ");
        Elements bases = doc.select("div.box-image > a > span > i");
        Elements titles = doc.select("div.box-contents > a > strong");
        Elements rates = doc.select(".percent span");
        Elements dates = doc.select(".txt-info strong");
//        Elements likes = doc.select(".count strong>i");

        List<CGVmovie> movieList = new ArrayList<>();
        for(int i = 0; i<ranks.size(); i++){
            if(crawRepository.findByTitle(titles.get(i).text()).size()!=0) {
                break;
            }
            String detaild = "http://www.cgv.co.kr"+dtail.get(i).attr("href");
            Document detailDoc = Jsoup.connect(detaild).get();

            String rank = ranks.get(i).text();
            String title = titles.get(i).text();

            Element titleEng2 = detailDoc.select("div.title > p").get(0);
            String titleEng = titleEng2.text();

            Element director2 = detailDoc.select(".spec > dl > dd:nth-child(2) > a").get(0);
            String director = director2.text();

            Element actor2 = detailDoc.select(".on").get(0);
            String actor = actor2.text();

            String img = imgs.get(i).attr("src");
            String rate = rates.get(i).text();

            Elements genres = detailDoc.select(".spec > dl > dt:nth-child(6)");
            String genre = genres.text();

            String base = bases.get(i).text();

            Elements details = detailDoc.select("div.sect-story-movie");
            String detail = details.text().substring(1,20);

            String date = dates.get(i).text();

            CGVmovie cgvMovie = new CGVmovie(rank, title, titleEng, director, actor, img, rate, genre, base, detail, date);

            crawRepository.save(cgvMovie);

            movieList.add(cgvMovie);
        }

        System.out.println(movieList);



        }




}
