package com.sm.backend.module.address.domain.service;

import com.sm.backend.common.response.PageResponse;
import com.sm.backend.module.address.domain.service.dto.*;
import com.sm.backend.module.address.infrastructure.client.KakaoLocalApiClient;
import com.sm.backend.module.address.infrastructure.client.MoisLocalApiClient;
import com.sm.backend.module.address.presentation.dto.*;
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
    private final MoisLocalApiClient moisLocalApiClient;

    public PageResponse<SearchAddressDto.Response> searchAddress(String query, int page, int size) {
        SearchAddress searchAddress = null;
        try {
            var response = kakaoLocalApiClient.searchAddressList(query, page, size);
            searchAddress = response.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR: {}", e.getMessage());
        }

        List<SearchAddressDto.Response> items = new ArrayList<>();
        for (var doc: searchAddress.getDocuments()) {
            items.add(SearchAddressDto.Response.builder()
                    .addressName(doc.getAddressName())
                    .build());
        }

        //위 for문을 java의 stream을 사용한 코드
//       var items = searchAddress.getDocuments().stream()
//               .map(it -> AddressDto.Response.builder().addressName(it.getAddressName()).build())
//               .collect(Collectors.toList());

        var meta = searchAddress.getMeta();

        var pageResponse = new PageResponse<SearchAddressDto.Response>();
        pageResponse.setPage(meta.getPageableCount(), meta.getTotalCount(), !meta.isEnd());
        pageResponse.setItems(items);

        return pageResponse;
    }

    public PageResponse<AdministrativeDistrictByCoordinatesDto.Response> searchAdministrativeDistrict(String x, String y, String inputCoord, String outputCoord) {
        AdministrativeDistrictByCoordinates administrativeDistrictByCoordinates = null;
        try {
            var coordinateResponse = kakaoLocalApiClient.searchCoordinateList(x, y, inputCoord, outputCoord);
            administrativeDistrictByCoordinates = coordinateResponse.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }

        List<AdministrativeDistrictByCoordinatesDto.Response> items = new ArrayList<>();
        for(var doc : administrativeDistrictByCoordinates.getDocuments()) {
            items.add(AdministrativeDistrictByCoordinatesDto.Response.builder()
                    .regionType(doc.getRegionType())
                    .build());
        }

        var meta = administrativeDistrictByCoordinates.getMeta();

        var pageResponse = new PageResponse<AdministrativeDistrictByCoordinatesDto.Response>();
        pageResponse.setPage(meta.getTotalCount());
        pageResponse.setItems(items);

        return pageResponse;
    }

    public PageResponse<ConversionAddressByCoordinatesDto.Response> convertAddressToCoordinates(String x, String y, String inputCoord) {
       ConversionAddressByCoordinates conversionAddressByCoordinates = null;
        try {
            var response = kakaoLocalApiClient.convertAddressToCoordinates(x, y, inputCoord);
            conversionAddressByCoordinates = response.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }

        List<ConversionAddressByCoordinatesDto.Response> items = new ArrayList<>();
        for(var docs : conversionAddressByCoordinates.getDocuments()) {
            items.add(ConversionAddressByCoordinatesDto.Response.builder()
                    .addressName(docs.getAddress().getAddressName())
                    .build());
        }

        var meta = conversionAddressByCoordinates.getMeta();

        var pageResponse = new PageResponse<ConversionAddressByCoordinatesDto.Response>();
        pageResponse.setPage(meta.getTotalCount());
        pageResponse.setItems(items);

        return pageResponse;
    }

    public PageResponse<ConversionCoordinatesSystemDto.Response> convertCoordinatesSystem(Double x, Double y, String inputCoord, String outputCoord) {
        ConversionCoordinatesSystem conversionCoordinatesSystem = null;
        try {
            var conversionCoordinatesSystemCall = kakaoLocalApiClient.convertCoordinatesSystem(x, y, inputCoord, outputCoord);
            conversionCoordinatesSystem = conversionCoordinatesSystemCall.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }

        List<ConversionCoordinatesSystemDto.Response> items = new ArrayList<>();
        for (var docs : conversionCoordinatesSystem.getDocuments()) {
            items.add(ConversionCoordinatesSystemDto.Response.builder()
                    .x(docs.getX())
                    .y(docs.getY())
                    .build());
        }

        var meta = conversionCoordinatesSystem.getMeta();

        var pageResponse = new PageResponse<ConversionCoordinatesSystemDto.Response>();
        pageResponse.setPage(meta.getTotalCount());
        pageResponse.setItems(items);

        return pageResponse;
    }
    //디버깅 결과 --> conversionCoordinatesSystem 이 왜 null 일까?

    public PageResponse<MoisRoadNameAddressDto.Response> searchMoisRoadNameAddress(String confmKey, int currentPage, int countPerPage, String keyword, String resultType, String hstryYn, String firstSort, String addInfoYn) {
        MoisRoadNameAddress moisRoadNameAddress = null;
        try {
            var response = moisLocalApiClient.searchRoadAddressList(confmKey, currentPage, countPerPage, keyword, resultType, hstryYn, firstSort, addInfoYn);
            moisRoadNameAddress = response.execute().body();
        } catch (IOException e) {
            log.error("Exception ERROR : {}", e.getMessage());
        }
        List<MoisRoadNameAddressDto.Response> items = new ArrayList<>();
        for(var doc : moisRoadNameAddress.getJusoList()) {
            items.add(MoisRoadNameAddressDto.Response.builder()
                    .roadAddr(doc.getRoadAddr())
                    .build());
        }

        var common = moisRoadNameAddress.getCommon();

        var pageResponse = new PageResponse<MoisRoadNameAddressDto.Response>();
        pageResponse.setPage(common.getTotalCount());
        pageResponse.setItems(items);


        return pageResponse;
    }
    //postman으로 찔러보기
}
// retrofit 통해서 주소 api가 어떻게 생성되는지 확인해보기.
// ~Client 라는 인터페이스를 제공
// ~Client 인터페이스 안에 정의한 메서드의 구현체를 service 단에서 처리.


// TODO step1 -> data.domain.page 로 감싸서 리턴
// Location 는 사실 (Page)Location 이다. -> 페이지 정보를 가지고있기 때문에
// 1. org.springframework.data.domain.Page 인터페이스에서 페이지 정보를 어떻게 다루고 있는지
// 구조가 어떻게 되어있는지 파악 Pageable 참고
// 페이지 구현체를 만드는 방법이 있는데 -> LocationDto -> Page<....> 페이지 구현체를 만드는 코드를 이용해서 변환
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



// TODO 1. 2개 정도 더 만들어보기 (지금 두개 만든거는 여러번 반복해보면서 익숙하게) -> OK.
// TODO 2. 지금까지 했던 거 궁금한거, 헷갈리는거.
// TODO 3. CS 를 다시 정리하고 공부하기 (노션에다가 CS 관련된 지식 (EX. filter..., queue...) / Q&A 형식으로 노션에다 정리.)
// TODO 4. 행안부 (https://business.juso.go.kr/addrlink/openApi/apiReqst.do) -> OK. ISSUE(EXCEPTION 문제 해결해야 함.)
// TODO 5. CS는 일주일에 10개씩 (노션에 정리하고 암기하기.)
// TODO 6. 8/6일에는 어떤 회사 가고싶은지 추리기. 8/14~~ 면접 예상.

// 카카오 오류 -> 행안부 대체 로직 작성.
// 카카오만 남겨놓고 행안부는 빼기
// @Headers의 Name : Value 수정하기.

// 제가 10개 내드린거 + 이력서 + 자기소개 준비
// 프로젝트 코드 정리 -> 주석 지우고 -> 코드 깔끔하게
// Github 꾸미기
// 코드 설명 가능해야 함.
