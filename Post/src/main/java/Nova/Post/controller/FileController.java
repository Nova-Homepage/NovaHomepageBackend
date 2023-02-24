package Nova.Post.controller;

import Nova.Post.domain.FileEntity;
import Nova.Post.service.FileService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor //생성자 생성과 의존관계를 진행해줌
@RequestMapping("/file") //해당 컨트롤러에서 /post를 가장 앞 uri에 위치시킨다.
@RestController
@Api(tags = {"File Info"}, description = "파일 서비스")
public class FileController {
    private final FileService fileService;
    //    @GetMapping("/images/{fileId}")
//    public String downloadImage(@PathVariable("fileId") Long id) throws IOException{
//
//
//        String aa = "<img src=\""+fileService.downloadImage(id).getFile() + "\">";
//        return aa;
////        return localService.downloadImage(id);
//    }
//
//    @GetMapping("/image/all")
//    public List<FileEntity> findfileAll()
//    {
//        return fileService.findAll();
//    }

    // 첨부 파일 다운로드
    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {

        FileEntity file = fileService.findById(id);

        UrlResource resource = new UrlResource("file:" + file.getFile_path());

        String encodedFileName = UriUtils.encode(file.getOriginalFileName(), StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자가 뜨도록 하는 헤더를 설정해주는 것
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }
}
