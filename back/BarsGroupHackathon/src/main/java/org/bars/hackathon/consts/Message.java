package org.bars.hackathon.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Справочник ошибок и их описания
 */
@Getter
@AllArgsConstructor
public enum Message {
    LOGIN_SUCCESS("Успешная авторизация", 701),
    ERROR("Ошибка выполнения запроса", 703),
    LOGIN_EXISTS("Логин уже существует", 704),
    AUTH_ERROR("Ошибка авторизации", 705),
    REGISTER_SUCCESS("Успешная регистрация", 710),
    LOGOUT_SUCCESS("Авторизационная сессия завершена", 711),
    LOGIN_INVALID("Некорректный логин", 715),
    IO_WRITE_EXCEPTION("При записи файла на диск произошла ошибка", 720),
    NO_HAVE_PERMISSIONS("Доступ запрещён", 727),
    PARAM_TYPE_NOT_SUPPORTED("Данный тип параметра не поддерживается", 730),
    USER_NOT_FOUND("Пользователь не найден", 731),
    ADMINISTRATOR_CREATE_SUCCESS("Администратор успешно зарегестрирован", 732),
    LOGIN_IS_EMPTY("Отсутствует логин. Обратитесь к администратору SUDIR", 737),
    ROLE_IS_EMPTY("Отсутствует роль. Обратитесь к администратору SUDIR", 738),
    ERROR_DELETE_FILE("Не удалось удалить файл", 746),
    PASSWORD_NOT_EQUAL("Пароли не совпадают", 750),
    PASSWORD_TOO_SHORT("Пароль менее 8 символов", 751),
    PASSWORD_IS_EMPTY("Введите пароль", 752),
    LOGIN_OR_PASSWORD_INVALID("Неправильный логин или пароль", 753);

    private final String message;

    private final int code;

    @Override
    public String toString() {
        return this.message;
    }
}