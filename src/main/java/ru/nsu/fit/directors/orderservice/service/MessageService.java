package ru.nsu.fit.directors.orderservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.orderservice.dto.response.MessageDto;
import ru.nsu.fit.directors.orderservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.orderservice.event.UserMessageEvent;

@ParametersAreNonnullByDefault
public interface MessageService {
    /**
     * Сохранить сообщение из чата от пользователя.
     *
     * @param userMessageEvent сообщение пользователя
     */
    void save(UserMessageEvent userMessageEvent);

    /**
     * Сохранить сообщение из чата от бизнеса.
     *
     * @param businessMessageEvent сообщение бизнеса
     */

    void save(BusinessMessageEvent businessMessageEvent);

    /**
     * Получить все сообщения для бизнеса.
     *
     * @param orderId    идентификатор заказа
     * @param businessId идентификатор бизнеса
     * @return список сообщений
     */
    @Nonnull
    List<MessageDto> getByBusiness(Long orderId, Long businessId);

    /**
     * Получить все сообщения для пользователя.
     *
     * @param orderId идентфиикатор заказа
     * @param userId  идентификатор пользователя
     * @return список сообщений
     **/
    @Nonnull
    List<MessageDto> getByUser(Long orderId, Long userId);
}
