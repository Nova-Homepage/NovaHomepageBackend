package Nova.Post.service;

import Nova.Post.Dto.BoardDto;
import Nova.Post.domain.BoardEntity;
import Nova.Post.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //생성자를 자동으로 생성해서 의존관계를 주입해줌?
public class BoardService {

    //DTO -> ENTITY
    //ENTITIY -> DTO
    //서비스 클래스에서 변환하는 과정이 많이 일어난다. VIEW에서 ENTITIY를 많이 노출시키지 않기 위해서 DTO를 사용한다.

    private final BoardRepository boardRepository; //필드주입

    @Transactional(readOnly = false)
    public BoardEntity save (BoardDto boardDto) throws IOException {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
            BoardEntity save = boardRepository.save(boardEntity);
            return save;
    }
    @Transactional(readOnly = false) //부모에서 자식 엔티티를 접그할때 트렌잭션을 선언해줘야한다
    public List<BoardDto> findAll()
    {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList)
        {
            boardDtoList.add(BoardDto.toBoardDto(boardEntity)); //entitiy -> dto
        }
        return boardDtoList;

    }
    @Transactional(readOnly = false)
    public BoardDto findById(Long id)
    {
        Optional<BoardEntity> Optinalpost = boardRepository.findById(id);
        if(Optinalpost.isPresent()) {
            BoardEntity boardEntity = Optinalpost.get();
            BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
            return boardDto;
        }
        else {
            return null;
        }
    }
    @Transactional(readOnly = false)
    public BoardDto upDate(BoardDto boardDto) //JPQ는 save 메서드를 통해서 insert update를 진행하는데, id값이 주어지면 update 없다면 insert를 실행한다.
    {

        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto);
        boardRepository.save(boardEntity);
        return findById(boardDto.getId());
    }

    //게시글 삭제
    @Transactional(readOnly = false)
    public void delete(Long id) {
        boardRepository.deleteById(id); //게시글 삭제
    }

    //페이징처리
    public Page<BoardDto> paging(Pageable pageable) {

        Page<BoardEntity> PageEntitiies = boardRepository.findAll(pageable);
        Page<BoardDto> postDtos = PageEntitiies.map(post -> new BoardDto(post.getId(),post.getTitle(),post.getContent(),post.getTag(),post.getCreatedDate(),post.getModifiedDate()
       ));
        return postDtos;

    }
}
