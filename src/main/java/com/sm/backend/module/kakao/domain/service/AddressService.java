package com.sm.backend.module.kakao.domain.service;

import com.sm.backend.common.response.PageResponse;
import com.sm.backend.module.kakao.domain.service.dto.Coordinate;
import com.sm.backend.module.kakao.infrastructure.client.KakaoLocalApiClient;
import com.sm.backend.module.kakao.domain.service.dto.Location;
import com.sm.backend.module.kakao.presentation.dto.AddressDto;
import com.sm.backend.module.kakao.presentation.dto.CoordinateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService {

    private final KakaoLocalApiClient kakaoLocalApiClient;

    public PageResponse<AddressDto.Response> searchAddress(String query, int page, int size) {
        Location location = null;
        try {
            var response = kakaoLocalApiClient.searchAddressList(query, page, size);
            location = response.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR: {}", e.getMessage());
        }

        List<AddressDto.Response> items = new ArrayList<>();
        for (var doc: location.getDocuments()) {
            items.add(AddressDto.Response.builder()
                    .addressName(doc.getAddressName())
                    .build());
        }

//        var items = location.getDocuments().stream()
//                .map(it -> AddressDto.Response.builder().addressName(it.getAddressName()).build())
//                .collect(Collectors.toList());

        var meta = location.getMeta();

        var pageResponse = new PageResponse<AddressDto.Response>();
        pageResponse.setPage(meta.getPageableCount(), meta.getTotalCount(), !meta.isEnd());
        pageResponse.setItems(items);

        return pageResponse;
    }

    public PageResponse<CoordinateDto.Response> searchAdministrativeDistrict(String x, String y, String inputCoord, String outputCoord) {
        Coordinate coordinate = null;
        try {
            var coordinateResponse = kakaoLocalApiClient.searchCoordinateList(x, y, inputCoord, outputCoord);
            coordinate = coordinateResponse.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }

        List<CoordinateDto.Response> items = new ArrayList<>();
        for(var doc : coordinate.getDocuments()) {
            items.add(CoordinateDto.Response.builder()
                    .regionType(doc.getRegionType())
                    .build());
        }

        var meta = coordinate.getMeta();

        var pageResponse = new PageResponse<CoordinateDto.Response>();
        pageResponse.setPage(meta.getTotalCount());
        pageResponse.setItems(items);

        return pageResponse;
    }


    /*

    // retrofit 통해서 주소 api 가 어떻게 생성되고 -> 서비스(service)가 몰라도 됨
    // AddressClient 라는 인터페이스를 제공
    // 구현체로 AddressKakaoClient, AddressNaverClient

    // KakaoAddressClient 인터페이스, 구현체
    // Naver ... 각각

//        Location.Meta metaInfo = location.meta;
//        int totalCount = metaInfo.getTotalCount();
          int totalCount = location.meta.getTotalCount();

        // 1. response 에 값이 잘 들어오는지 디버깅 - > response에는 값이 잘 들어옴.
        // 2. 잘 들어오면 response 에 있는 값을 꺼내서 -> Page<AddressDto.CreateResponse> 에 맞게 변환



        // TODO CreateResponse 안에 필드들을 카멜케이스로  ==> ok.

        // TODO step1 -> data.domain.page 로 감싸서 리턴
        // Location 는 사실 (Page)Location 이다. -> 페이지 정보를 갖고 있음
        // 1. org.springframework.data.domain.Page 인터페이스에서 페이지 정보를 어떻게 다루고 있는지
            // 구조가 어떻게 되어있는지 파악 Pageable 참고
            // 페이지 구현체를 만드는 방법이 있는데 -> LocationDto -> Page<....> 페이지 구현체를 만드는
        // 코드를 이용해서 변환
            // Page<....> abc = ABC.create(size, item)
        // 2. 페이지 구현체


        // TODO step2 -> PageResponse 만들어서 리턴  (step1 리팩토링)
        // 고려해야할 건
        // data.domain.page 에 있는 Page 로 감싸서 리턴을 해주면
        // 프론트에서는 필요 없는 필드들이 있을 건데,
            // 페이지네이션을 프론트에서 구현하기 위해서 백엔드에서 넘겨줘야할 꼭 필수인 필드들이 무엇인지를 생각해보기
            // data.domain.page 에 있는 여러 필드들이 꼭 프론트에서 전부 다 필요한 것인지 ?
            // d.d.p 10개의 필드가 있는데, 4개만있어도 프론트에서 페이지네이션을 구현하는데 문제가 없다고하면 ...
        // 커스텀한 페이지 리스폰스를 만들어줄 수 있겠죠
        // PageResponse -> BaseResponse 참고


        // TODO 변환 로직 구현하고 -> kakao api 에서 2개더 구현 (꼭 !!) -> 이거 구현 못하면 3개월 수습기간 탈락한다는 생각으로
            // 수단과 방법을 가리지 말고
            // 구글 -> 영어로 -> 유튜브로 -> 강의 -> 단톡방 힌트 -> OKKY 커뮤니티
            // 100% 과제 제출 한다는 식으로

     */



    // TODO 1. 2개 정도 더 만들어보기 (지금 두개 만든거는 여러번 반복해보면서 익숙하게
    // TODO 2. 지금까지 했던 거 궁금한거, 헷갈리는거.
    // TODO 3. CS 를 다시 정리하고 공부하기 (노션에다가 CS 관련된 지식 (EX. filter..., queue...) / Q&A 형식으로 노션에다 정리.)
    // TODO 4. 행안부 (https://business.juso.go.kr/addrlink/openApi/apiReqst.do) / 카카오 주소 api가 먹통이면 행안부를 사용할 수 있도록.
    // TODO 5. CS는 일주일에 10개씩 (노션에 정리하고 암기하기.)
    // TODO 6. 8/6일에는 어떤 회사 가고싶은지 추리기. 8/14~~ 면접 예상.

}
