package Nova.Post.service;

import Nova.Post.Dto.FileDto;
import Nova.Post.domain.FileEntity;
import Nova.Post.domain.BoardEntity;
import Nova.Post.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //생성자를 자동으로 생성해서 의존관계를 주입해줌?
public class FileService {

    private final FileRepository fileRepository;

    @Transactional(readOnly = false)
    //파일업로드
    public void uploadfile(List<MultipartFile> files, BoardEntity boardEntity) throws IOException {

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
            String savedPath = "C:\\Users\\82103\\Desktop\\postupload\\" + savedName;


            FileDto fileDto = new FileDto();
            fileDto.setOriginalFileName(originalFilename);
            fileDto.setStoredFileName(savedName);
            fileDto.setFile_path(savedPath);
            fileDto.setFile_size(file.getSize());
            fileDto.setFile_type(file.getContentType());
            fileDto.setBoardEntity(boardEntity);

        // 실제로 로컬에 uuid를 파일명으로 저장
        file.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
        FileEntity savedFile = fileRepository.save(FileEntity.toSaveFileEntity(fileDto));

        }
//        return savedFile.getId(); //업로드된 파일이 무엇인지 확인할 때 유용할듯

    }

    public Resource downloadImage(Long id) throws MalformedURLException {
        FileEntity fileEntity = fileRepository.findById(id).get();
        return new UrlResource("file:" + fileEntity.getFile_path());
    }
    public List<FileEntity> findAll()
    {
        return  fileRepository.findAll();
    }
    public FileEntity findById(Long id)
    {
        return fileRepository.findById(id).get();
    }
}
