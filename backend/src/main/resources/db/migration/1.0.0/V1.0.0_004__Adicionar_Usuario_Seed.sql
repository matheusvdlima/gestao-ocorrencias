/* Descrição:
 * Adicionar usuário inicial do sistema (seed);
 *
 * Senha: 123456
 *
 * Autor: Matheus Lima
 * Data: 26/08/2025
 */

INSERT INTO public.tb_usuario (id, nome, email, senha, perfil, data_criacao)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'admin',
    'admin@incidents.com',
    '$2a$10$7Q0kFZpWQ0Kh3yb6jZKXzO9M6u5kbYo/uEHVl2PnQXtyjd1sRScg2',
    'ADMIN',
    NOW()
);
