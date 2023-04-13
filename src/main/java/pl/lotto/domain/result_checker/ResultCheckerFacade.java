package pl.lotto.domain.result_checker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbers_generator.NumbersGeneratorFacade;
import pl.lotto.domain.numbers_generator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbers_receiver.NumberReceiverFacade;
import pl.lotto.domain.numbers_receiver.dto.TicketDto;
import pl.lotto.domain.result_checker.dto.TicketCheckedDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ResultCheckerFacade {

    private final NumbersGeneratorFacade numbersGenerator;
    private final NumberReceiverFacade numberReceiverFacade;
    private final DrawDateFacade drawDateFacade;


    List<TicketCheckedDto> generateWinningTickets() {
        LocalDateTime dateOfNextDraw = drawDateFacade.getDateOfNextDraw();

        return checkWinnerResults(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(dateOfNextDraw),
                numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(dateOfNextDraw));
    }

    private List<TicketCheckedDto> checkWinnerResults(List<TicketDto> ticketDtos, WinnerNumbersDto winnerNumbersDto) {
        List<TicketCheckedDto> listToReturn = new ArrayList<>();

        if (winnerNumbersDto == null || winnerNumbersDto.winningNumbers().isEmpty()){
            return List.of(TicketCheckedDto.builder()
                    .message("Ticket checked incorrectly")
                    .isWinner(false).build());
        }

        for (TicketDto ticketDto : ticketDtos) {
            List<Integer> setToRetain = new ArrayList<>(ticketDto.numbersFromUser());
            setToRetain.retainAll(winnerNumbersDto.winningNumbers());
            int result = setToRetain.size();
            boolean isWinner = result >= 3;

            TicketCheckedDto resultOfResultCheckerDto = TicketCheckedDto.builder()
                    .isWinner(isWinner)
                    .winnersNumbers(winnerNumbersDto.winningNumbers())
                    .ticketDto(ticketDto)
                    .message("Ticket checked correctly")
                    .build();
            listToReturn.add(resultOfResultCheckerDto);
        }
        return listToReturn;
    }
}