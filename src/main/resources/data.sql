INSERT INTO golongan ("id_golongan", "is_active", "nama", "pajak", "rate") VALUES ('99', '0', '99', '0', '0'), ('1', '1', '1', '2', '200000') ON CONFLICT DO NOTHING;
INSERT INTO roles ("id_role", "nama") VALUES (1, 'ADMIN'), (2, 'DIREKTUR'), (3, 'PENGAJAR'), (4, 'STAF CABANG'), (5, 'CALON PENGAJAR') ON CONFLICT DO NOTHING;
INSERT INTO users ("id_user", "alamat", "created_at", "email", "is_active", "mata_pelajaran", "nama", "nama_bank", "nip", "no_rekening", "no_telp", "password", "tgl_lahir", "golongan_pegawai", "role") VALUES ('40288189716cdd4401716cdff3c90000', 'ADMIN', '2020-04-12', 'admin@bta.com', b'1', NULL, 'Admin', NULL, 'admin', NULL, '00000000000', '$2a$10$Qufuh87E8fZnV6f/bqb/SOjLLfcm6KQz1uLpfFhT4O.E2IvF.9pZm', '1999-12-31', NULL, '1'), ('40288189716ec1f001716ec335cd0000', 'AlamatDirektur', '2020-04-12', 'direktur@bta.com', b'1', NULL, 'Direktur', 'Bank', '20201202001', '391830289', '302983019', '$2a$10$kYs2ssmlT0nXeLzI.1/.Qu7fP4oCA7t8e76aS9egmo2bnDMVMJqii', '2020-01-01', '1', '2'), ('40288189716ec1f001716ec76cef0001', 'AlamatPengajar', '2020-04-12', 'pengajar@bta.com', b'1', 'Matematika', 'Pengajar', 'Bank', 'MTK20201202002', '32098382928', '139082309', '$2a$10$YNcqAi34lXC18WZu7nHCyeHcU6QNRbXmIEOCtKOpTJZcXmrnLrQRu', '2020-01-01', '1', '3'), ('40288189716ec1f001716ec85a3d0002', 'AlamatStafCabang', '2020-04-12', 'stafcabang@bta.com', b'1', NULL, 'Staf Cabang', 'Bank', '20201202003', '31932983', '39130280929', '$2a$10$C8.cQ/mqRuhtWOo/Sfbh6exGa38rIIf1bvEjyOMlhNysqRN6VOee6', '2020-01-01', '1', '4') ON CONFLICT DO NOTHING;
