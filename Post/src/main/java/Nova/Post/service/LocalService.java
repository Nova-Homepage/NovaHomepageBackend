package Nova.Post.service;

import Nova.Post.Dto.FileDto;
import Nova.Post.domain.FileEntity;
import Nova.Post.domain.Post;
import Nova.Post.repository.PostFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //생성자를 자동으로 생성해서 의존관계를 주입해줌?
public class LocalService {

    private final PostFileRepository postFileRepository;

    @Transactional(readOnly = false)
    //파일업로드
    public void uploadfile(List<MultipartFile> files, Post post) throws IOException {

        for(MultipartFile file : files) {

            //원래 파일 이름 추출
            String originalFilename = file.getOriginalFilename();

            //파일이름으로 쓸 UUID 생성
            String uuid = UUID.randomUUID().toString();

            // 확장자 추출(ex : .png)
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // uuid / 원래이름 / 확장자 결합
            String savedName = uuid + originalFilename+ extension;

            // 파일을 불러올 때 사용할 파일 경로
            String savedPath = "C:\\Users\\82103\\Desktop\\upload\\" + savedName;


            FileDto fileDto = new FileDto();
            fileDto.setOriginalFileName(originalFilename);
            fileDto.setStoredFileName(savedName);
            fileDto.setFile_path(savedPath);
            fileDto.setFile_size(file.getSize());
            fileDto.setFile_type(file.getContentType());
            fileDto.setPost(post);

        // 실제로 로컬에 uuid를 파일명으로 저장
        file.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
        FileEntity savedFile = postFileRepository.save(FileEntity.toSaveFileEntity(fileDto));

        }
//        return savedFile.getId(); //업로드된 파일이 무엇인지 확인할 때 유용할듯

    }

    public Resource downloadImage(Long id) throws MalformedURLException {
        FileEntity fileEntity = postFileRepository.findById(id).get();
        return new UrlResource("file:" + fileEntity.getFile_path());
    }
    public List<FileEntity> findAll()
    {
        return  postFileRepository.findAll();
    }
    public FileEntity findById(Long id)
    {
        return postFileRepository.findById(id).get();
    }
}
