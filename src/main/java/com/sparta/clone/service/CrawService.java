package com.sparta.clone.service;

import com.sparta.clone.domain.CGVmovie;
import com.sparta.clone.repository.CrawRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrawService {

    private final CrawRepository crawRepository;

    //크롤링할 주소 입력
    private static String CGV_MOVIE_URL = "http://www.cgv.co.kr/movies/";

    @PostConstruct
    public void getCGVMovie() throws IOException {

        //크롤링한 주소를 doc에 입력
        Document doc = Jsoup.connect(CGV_MOVIE_URL).get();

        //크롤링한 세부적인 부분 선택
        Elements ranks = doc.select(".rank");
        Elements imgs = doc.select(".thumb-image > img");
        Elements dtail = doc.select("div.box-image > a ");
        Elements bases = doc.select("div.box-image > a > span > i");
        Elements titles = doc.select("div.box-contents > a > strong");
        Elements rates = doc.select(".percent span");
        Elements dates = doc.select(".txt-info strong");

        //크롤링한 movie를 담아줄 리스트 생성
        List<CGVmovie> movieList = new ArrayList<>();

        //for문을 통해 movieList에 넣어줄 movie 정보 생성
        for(int i = 0; i<ranks.size(); i++){
            if(crawRepository.findByTitle(titles.get(i).text()).size()!=0) {
                break;
            }

            //상세페이지 정보를 불러오기위해 해당 영화 상세페이지 주소 불러오기
            String detaild = "http://www.cgv.co.kr"+dtail.get(i).attr("href");
            //상세페이지 주소를 detaildoc에 입력
            Document detailDoc = Jsoup.connect(detaild).get();

            //무비차트페이지에도 있는 정보는 위에서 만들었으므로 그대로 넣어주기
            String rank = ranks.get(i).text();
            String title = titles.get(i).text();

            //상세페이지 정보는 위에서 select를 안했으므로 select과 동시에 해당 정보 입력
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
            String detail = details.toString();


            String date = dates.get(i).text();

            CGVmovie cgvMovie = new CGVmovie(rank, title, titleEng, director, actor, img, rate, genre, base, detail, date);

            //for문으로 짜진 movie 저장
            crawRepository.save(cgvMovie);

            //잘저장되었는지 리스트에 더한 후 출력
            movieList.add(cgvMovie);
        }
        //System.out.println("크롤링 완료");
        //System.out.println(movieList);



        }




}
