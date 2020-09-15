insert into users
    (email, enabled, first_name, last_name, password, role)
    values
    (
     'admin@admin.com',
     true,
     'Админ',
     'Главный',
     '$2a$10$w69v/229Q7NSBK.EKOtSxegaQrfs5PP8/j2D.E8SxK7V5292I.Kki',
     'ROLE_ADMIN'
);