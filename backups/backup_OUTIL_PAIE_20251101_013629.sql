-- Sauvegarde de la base de données: OUTIL_PAIE
-- Date: 2025-11-01T01:36:30.085129500


-- Table: absence

-- Table: administrateur
INSERT INTO "administrateur" VALUES (1, 'SUPER_ADMIN');

-- Table: administrateur_configurateur

-- Table: administrateurs

-- Table: attestation

-- Table: bareme_ir

-- Table: categorie_salariale

-- Table: configurateur
INSERT INTO "configurateur" VALUES (5, 'Région de Fès-Meknès');

-- Table: configurateurs

-- Table: conge

-- Table: constantes

-- Table: contrat

-- Table: cotisation

-- Table: declaration_sociale

-- Table: demande_document

-- Table: echelon

-- Table: element_variable_paie

-- Table: employe_simple
INSERT INTO "employe_simple" VALUES (NULL, 26, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 27, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 28, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 29, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 31, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 32, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 33, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 34, NULL, NULL, NULL);
INSERT INTO "employe_simple" VALUES (NULL, 35, NULL, NULL, NULL);

-- Table: employes_simple

-- Table: fiche_paie

-- Table: grille_salariale

-- Table: jour_ferie

-- Table: mesure

-- Table: motif_mesure

-- Table: parametrage_bulletin

-- Table: parametrage_bulletin_cotisation

-- Table: parametrage_bulletin_rubrique

-- Table: parametrage_etat

-- Table: parametrage_journal

-- Table: parametrage_journal_rubrique

-- Table: parametres_systeme

-- Table: periode_paie

-- Table: prime_indemnite_retenue

-- Table: profil_salarial

-- Table: remuneration

-- Table: resultat_paie

-- Table: resultat_rubrique

-- Table: rh

-- Table: role
INSERT INTO "role" VALUES (1, 'ADMIN');
INSERT INTO "role" VALUES (2, 'RH');
INSERT INTO "role" VALUES (3, 'CONFIGURATEUR');
INSERT INTO "role" VALUES (4, 'EMPLOYE');

-- Table: rubrique_bulletin

-- Table: rubrique_paie

-- Table: sauvegarde_bdd
INSERT INTO "sauvegarde_bdd" VALUES (1, '2025-11-01 01:16:57.074636', './backups', '173ab992-20d0-4957-9ba7-f79441f57fda', 'backup_OUTIL_PAIE_20251101_011657.sql', 'Manuel', 'failed', 0);
INSERT INTO "sauvegarde_bdd" VALUES (1, '2025-11-01 01:17:10.464277', './backups', 'd000ab3f-c1d2-4a53-bbf1-7bdceb3d8664', 'backup_OUTIL_PAIE_20251101_011710.sql', 'Manuel', 'failed', 0);
INSERT INTO "sauvegarde_bdd" VALUES (1, '2025-11-01 01:19:24.149001', './backups', '84cbe3cf-28e5-4942-bbfa-c0018336e19c', 'backup_OUTIL_PAIE_20251101_011924.sql', 'Manuel', 'failed', 0);
INSERT INTO "sauvegarde_bdd" VALUES (1, '2025-11-01 01:32:17.45039', './backups', '04839669-f62e-405c-8261-8560ddca9548', 'backup_OUTIL_PAIE_20251101_013217.sql', 'Manuel', 'success', 16641);
INSERT INTO "sauvegarde_bdd" VALUES (1, '2025-11-01 01:33:24.356134', './backups', 'cc9b87ec-e712-44f9-ad55-7a66b324eda7', 'backup_OUTIL_PAIE_20251101_013324.sql', 'Manuel', 'success', 16831);
INSERT INTO "sauvegarde_bdd" VALUES (1, '2025-11-01 01:36:29.954189', './backups', 'f61ff037-8dd7-4446-84f0-a0190df08252', 'backup_OUTIL_PAIE_20251101_013629.sql', 'Manuel', 'pending', 0);

-- Table: societe
INSERT INTO "societe" VALUES (NULL, NULL, NULL, 'test', NULL, 'jgrrg', 'st', 'youssef.benslimane@company.com', '', '1760969831622', 'i,,fie', NULL, 'st', '', NULL, NULL, '', 'villetest');

-- Table: societe_configurateur

-- Table: solde_conges

-- Table: statut_salarial

-- Table: type_absence

-- Table: type_attestation

-- Table: type_contrat

-- Table: type_cotisation

-- Table: type_mesure

-- Table: type_motif_mesure

-- Table: type_prime_indemnite_retenue

-- Table: unite_organisationnelle
INSERT INTO "unite_organisationnelle" VALUES (NULL, NULL, 'HR001', NULL, NULL, 'U001', 'Ressources Humaines', NULL, 'Département');

-- Table: utilisateur
INSERT INTO "utilisateur" VALUES ('2025-08-15 10:36:34.553247', '2025-11-15 10:36:34.553247', NULL, NULL, 1, 1, 'abdoubensalek7@gmail.com', 'ACTIF', 'Admin', '{bcrypt}$2a$10$mQ2DtqmL7BfJfoxBHptd6.D8iXQk1GZP.ZleGGvm0dqaxo5YPNQiy', 'Super', NULL, 'admin', NULL);
INSERT INTO "utilisateur" VALUES ('2025-08-15 12:43:27.157663', '2025-11-15 13:08:54.803333', NULL, NULL, 3, 5, 'youssef.benslimane@innovex-consulting.fr', 'ACTIF', 'Amine', '{bcrypt}$2a$10$5WaXnabOmAuECzfkC2iOxuTm5UAEUpuokbARNKnGH2VYbx/13C41e', 'El Fassi', NULL, 'config_amine', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-11 16:15:16.404', '2025-12-11 16:17:16.734161', NULL, NULL, 4, 34, 'abrfmu9npn@osxofulk.com', 'ACTIF', 'rabati', '{bcrypt}$2a$10$Y3a43.u3ve7PHumR./Pcxe10stSKWufpLQnl0j.YB6ducTqAa4RwO', 'mohamed amin', NULL, 'Aminmoha', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 09:28:34.138627', '2025-12-15 09:29:55.952033', NULL, NULL, 4, 35, 'tasen12252@ekuali.com', 'ACTIF', 'bim', '{bcrypt}$2a$10$P.Sn31T8Xdt3NzlkvAgzl.AXxS.iCrgdudeO6KBhzYy9vqUQRHtGa', 'makrim', NULL, 'mabim', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 13:51:42.883789', '2025-12-15 15:19:53.84463', NULL, NULL, 4, 40, 'cackaatb6e@pngrise.com', 'ACTIF', 'Bernard', '{bcrypt}$2a$10$zCQdKsxgB/vdc3wQIa0pSeSJNY667HH9aabW0K9wh4UGAUSaYrLLS', 'Sarah', NULL, 's.bernard', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 16:12:30.387554', '2026-03-15 16:12:30.640018', NULL, NULL, 4, 45, 'nizwig5nhb@comyze.org', 'ACTIF', 'Martin', '{bcrypt}$2a$10$IhIhJqZ/G/n3H28tLfWj4ej/T4JPAzCoTeNjWMNQvTIu/0zh4Ue/e', 'Sophie', NULL, 'employe2', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 16:14:27.038266', '2026-03-15 16:14:27.154831', NULL, NULL, 4, 46, 'gedos28646@poesd.com', 'ACTIF', 'Martin', '{bcrypt}$2a$10$sH68oxPdnNBtpVNcFNIwk.ENgGoaIENG3FQJYnfFn3pNmlrqhBIdy', 'Sophie', NULL, 'employe3', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 16:17:01.441598', '2026-03-15 16:17:01.698587', NULL, NULL, 4, 47, 'himasi5435@ishense.com', 'ACTIF', 'Martin', '{bcrypt}$2a$10$wsZK5oU6yaTJeiiuBXNTS.ER9lznBk2KDIUmEKtHCV3gwMTmErD/.', 'Sophie', NULL, 'employe4', NULL);
INSERT INTO "utilisateur" VALUES ('2025-08-15 11:56:52.176695', '2026-03-15 16:29:14.036026', NULL, NULL, 3, 3, 'abderrahmane.bensalek@innovex-consulting.fr', 'ACTIF', 'Test', '{bcrypt}$2a$10$e5qCtAJTwH6nsU7BHov9DuiSjuJ5vFjIpzFQIDyR/6O8KtSwNa6eK', 'Final', NULL, 'config_test_final', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-01 13:31:10.05029', '2026-03-15 16:29:14.036026', NULL, NULL, 1, 9, 'hiwojev565@besaies.com', 'ACTIF', 'hmed', '{bcrypt}$2a$10$LyiOI4FRqUB0vxI91XLYsubt0T.DfeuWzo5Xb1Z1uTPSgJvR0sfei', 'sihmed', NULL, 'hmaad', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-01 13:39:49.032881', '2026-03-15 16:29:14.036026', NULL, NULL, 2, 12, 'segaxo7828@noidem.com', 'ACTIF', 'renault', '{bcrypt}$2a$10$kRrqvtylKsQm1G7j/.J7oej2Bs7aPCySYUnS6YSQTyIqTkgghBl2.', 'nasser', NULL, 'mariot', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 16:34:52.135006', '2025-09-18 16:34:52.313553', NULL, NULL, 4, 49, 'mikoxac827@ishense.com', 'EN_ATTENTE_CHANGEMENT_MDP', 'Martin', '{bcrypt}$2a$10$PWRCUbEocMzy/4aqhUO1YO4ii/nweDkpmFipb/XkIP6GkRYGv8hP6', 'Sophie', NULL, 'elhajabdeou', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:02:03.44574', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 14, 'lihado2403@cspaus.com', 'ACTIF', 'marjane', '{bcrypt}$2a$10$RjXD131Zz.PiqN9lOPnDFe0KdcGM/b129Szo1bJeQ1Z7RmPv21GNK', 'said', NULL, 'aidisaidi', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:07:03.068199', '2026-03-15 16:29:14.036026', NULL, NULL, 3, 16, 'higijag916@mirarmax.com', 'ACTIF', 'rayes', '{bcrypt}$2a$10$xg83UEG.O1VR85vW5f7Y1uRBORATPykHKaHbKIG2bsoaeNRv.AU0i', 'sihmeded', NULL, 'karimo', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:14:36.700105', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 17, 'k170gnyorh@mrotzis.com', 'ACTIF', 'ilyassi', '{bcrypt}$2a$10$.23FD2/rHCYC9FNvOLtkzeeM9uY3sOXEpoRtfvNlmDJ/ZyumeCpdC', 'test450', NULL, 'izdihar', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:35:41.256776', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 21, 'cau53530@toaik.com', 'ACTIF', 'batista', '{bcrypt}$2a$10$xVKdnVPGc8Wb5lM.2jiCiOcoCOLLilD/pqbji/2c.0XxIPoOJ468K', 'ranim', NULL, 'rano', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:36:35.698469', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 22, 'soley87651@certve.com', 'ACTIF', 'maka', '{bcrypt}$2a$10$R.yJOqk1aW0QxKFJNckNtuo2/dlrOuemzx5n2xASahuLzb1Z9BJm6', 'saka', NULL, 'darouin', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:39:41.115995', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 23, 'lefeleh214@dpwev.com', 'ACTIF', 'frez', '{bcrypt}$2a$10$1c2/ZnGIHBgAQ8tG9aJPl.31t1cXtPjwi09aqOPQDYpQUC1qWAkTq', 'said', NULL, 'zijiazs', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 15:37:05.978387', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 27, 'kifixat517@inupup.com', 'ACTIF', 'testteur', '{bcrypt}$2a$10$UROy.SA815x2mKNph/nZn.Wqg8Nda1IXnvd/BihWaNy5TbaqjVr/q', 'said ', NULL, 'marsaid', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 22:20:31.768704', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 28, '8rreycl3fq@bwmyga.com', 'ACTIF', 'boyob', '{bcrypt}$2a$10$X07AwD6IPUSpEwIDQ7i5SuZ0UmZY5BWlhG7uj2A5K1.hKu9nQEzB.', 'uyob', NULL, 'ranboww', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 22:22:00.699788', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 29, 'babova5137@certve.com', 'ACTIF', 'bana', '{bcrypt}$2a$10$jiNE.eJp5A8ldYU5ToCjAuHesp6IlI0HfUA.nz9mAx8bSdFViZ5Iu', 'rim', NULL, 'oupxwaw', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 13:26:47.318919', '2026-03-15 16:29:14.036026', NULL, NULL, 1, 37, 'fivex33510@reifide.com', 'ACTIF', 'kl,lk', '{bcrypt}$2a$10$wUF9sm1L6jVOx9FDKffLO.hk2XL8sjEcOuPtHecoqiE0AG6FFxkPe', 'sihmed', NULL, 'azdnlod', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 13:49:34.579241', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 39, 'sophie.martin@entreprise.com', 'ACTIF', 'Martin', '{bcrypt}$2a$10$TaK6X3fNTsgDop1Hqr7kWe7fJXAoEQBoOlhZpsmXK5EhCLT2bry9G', 'Sophie', NULL, 'employe01', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 15:35:55.428741', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 44, 'cezvut0gfy@vracv.com', 'ACTIF', 'Martin', '{bcrypt}$2a$10$oPIoI0OeCfUo01ODrf.BfeDTdmHpSVoCAs2h9BbeOy94pCXacxvcq', 'Sophie', NULL, 'employe', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 16:20:15.277301', '2026-03-15 16:29:14.036026', NULL, NULL, 4, 48, 'detaji5629@poesd.com', 'ACTIF', 'Martin', '{bcrypt}$2a$10$uavT9Rkvy1QQEje/r81ixOKF2HH8S.3ccGpAhLTwtQS4IHl24Zufy', 'Sophie', NULL, 'elhaj', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 17:00:26.689033', '2026-03-15 17:00:26.780364', NULL, NULL, 4, 52, 'mejoji2244@poesd.com', 'ACTIF', 'Email', '{bcrypt}$2a$10$8F74Br9NoMjpC8/NmGN3pumLNstzMzFk6buZsqcdIW5DfZBOLf8GC', 'Test', NULL, 'email', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 17:08:43.146097', '2026-03-15 17:08:43.316252', NULL, NULL, 4, 54, 'mipovek136@ekuali.com', 'ACTIF', 'Final', '{bcrypt}$2a$10$YP/C034OuwMmb8Znrc9BfeGr8i6H1e0bP1QanJoPx0lvmtY8wdHtq', 'Test', NULL, 'employe.final.test', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 17:15:38.787133', '2026-03-15 17:15:38.932434', NULL, NULL, 4, 57, 'jedafi1860@fanwn.com', 'ACTIF', 'Final', '{bcrypt}$2a$10$TVmYHW3Gc3x98SNu7tp44uts.neZJeX9QCo08oqKAdLIoZPhlgaM6', 'Test', NULL, 'empll', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-17 23:56:12.517185', '2026-03-17 23:56:12.704265', NULL, NULL, 2, 58, 'sophie.dupont@example.com', 'ACTIF', 'Dupont', '{bcrypt}$2a$10$nkiN12HncBECdWVDSgAxZOHa1cX3a1tKSQLdZKONRX.YlzKQgjH6K', 'Sophie', NULL, 'rhuser', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-18 00:09:22.808841', '2026-03-18 00:09:23.151623', NULL, NULL, 2, 60, 'nejoti9614@ekuali.com', 'ACTIF', 'Dupont', '{bcrypt}$2a$10$2ji2uMA7fKwde3eoAO.FwO.B67jLzQ8yOnqrAyH7w93wycuAXJ8cO', 'Sophie', NULL, 'Rhuser', NULL);
INSERT INTO "utilisateur" VALUES ('2025-10-16 10:34:26.901522', '2026-04-16 10:34:27.344562', NULL, NULL, 3, 61, 'lecihi5667@fixwap.com', 'ACTIF', 'Benali', '{bcrypt}$2a$10$pUSRHw3xeNZ9645YUDTKWuFDModZXOgpjpIIFABxP2BkggE.dn/xW', 'Sofia', NULL, 'config2', NULL);
INSERT INTO "utilisateur" VALUES ('2025-10-17 14:39:09.030505', '2026-04-17 14:39:09.342321', NULL, NULL, 4, 62, 'jedonox136@fixwap.com', 'ACTIF', 'yasser', '{bcrypt}$2a$10$mlhxFhX/93J.Yzuhc3cIx.AcpBVzsbb0vbZR3EfgpxJbFwQ1wHcYK', 'karoum', NULL, 'emp003', NULL);
INSERT INTO "utilisateur" VALUES ('2025-10-17 16:04:40.422575', '2026-04-17 16:04:40.571437', NULL, '2025-10-17 16:04:40.422575', 1, 64, 'j78tox1ynl@wyoxafp.com', 'ACTIF', 'abdelhamid', '{bcrypt}$2a$10$tNEQ8sEUd022Xd0jYzXH0ONS1NAr71nr.gfVApNor9RDJI/zDw9O.', 'saoud', NULL, 'marin', NULL);
INSERT INTO "utilisateur" VALUES ('2025-10-17 16:56:23.288926', '2026-04-17 16:56:23.838049', NULL, '2025-10-17 16:56:23.288926', 4, 65, '7ee2ecixds@mkzaso.com', 'ACTIF', 'mahmoud', '{bcrypt}$2a$10$R2KSfBpj0vF8DJtG0rz7U.ShuRJ/Q3i5C7xVDkO7PNolPPAGF/Sj2', 'karim', NULL, 'karimod', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:27:02.817607', '2026-03-15 16:29:14.036026', NULL, '2025-10-20 16:13:30.585727', 4, 20, 'hajmh4ampv@mkzaso.com', 'BLOQUE', 'kae,d', '{bcrypt}$2a$10$XqtXo88EK1RaXiy1ijwTOuR0TuAhiDs6Epb6whtk1vNMLfEY51K6.', 'splpa', NULL, 'akd,k,akdfer', NULL);
INSERT INTO "utilisateur" VALUES ('2025-10-20 15:08:09.670389', '2026-04-20 15:08:09.872281', NULL, '2025-10-20 15:08:09.670389', 3, 71, 'dirola1002@nrlord.com', 'ACTIF', 'akouk', '{bcrypt}$2a$10$IKXu/bvmdhFYK69KR.fP9uq5ZsbsIGhnVDryI091cLfKWso7ylRy2', 'TESTKz', NULL, 'lundi', 'jd,oiajef');
INSERT INTO "utilisateur" VALUES ('2025-10-20 16:31:08.822039', '2026-04-20 16:31:09.152884', NULL, '2025-10-20 16:31:08.822039', 4, 74, 'fotep94851@fixwap.com', 'ACTIF', 'mest', '{bcrypt}$2a$10$BXS0NKeja/bqr/spRCdBV.Jp2/jNdp01mBGedxNQ1MIaUKa/r4gBi', 'testeur', NULL, 'useeer', 'uh_séhu');
INSERT INTO "utilisateur" VALUES ('2025-10-20 10:09:52.619524', '2026-04-20 10:09:52.782255', NULL, '2025-10-20 10:09:52.619524', 3, 69, 'geveroy271@fixwap.com', 'ACTIF', 'simo', '{bcrypt}$2a$10$S3AbIpBIWttxwnH8yaJGCOtCnbomavUd3FDoTztZn4mzXkGM5o6aq', 'simo', NULL, 'Karim', 'smollay');
INSERT INTO "utilisateur" VALUES ('2025-10-23 10:14:04.335604', '2026-04-23 10:14:04.518233', NULL, '2025-10-23 10:14:04.335604', 2, 76, 'macir97414@datoinf.com', 'ACTIF', 'achraf', '{bcrypt}$2a$10$1AAsrxTuYW7vb53RRcPgqOpHg8/5YKfpjEeWlJEFpSE1flfWUASPu', 'mazouz', NULL, 'maroc', 'mamama4');
INSERT INTO "utilisateur" VALUES ('2025-10-19 13:18:48.314815', '2026-04-21 10:14:01.798157', NULL, '2025-10-21 10:14:01.798157', 1, 68, 'wamex39172@elygifts.com', 'ACTIF', 'ka,kfk', '{bcrypt}$2a$10$dMsUF141ayg1AhKnBJ4OlOlr3nyOGi5w28VGJDlvbN7k8lki3CAWW', 'ke,fk', NULL, 'ahmouma', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-15 16:48:19.082225', '2025-09-18 16:48:19.212689', NULL, '2025-10-21 10:15:17.846682', 4, 50, 'kikewe3851@merumart.com', 'ACTIF', 'Test', '{bcrypt}$2a$10$7cZAE9qBPAPO1dcyJo6E0.93VmAGcTgq1klDHlDfoBnN7Y4jL3BGy', 'Employe', NULL, 'tmemp', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 23:00:52.490706', '2026-04-21 10:38:11.248099', NULL, '2025-10-21 10:38:11.248099', 4, 31, 'cil97557@toaik.com', 'ACTIF', 'banda', '{bcrypt}$2a$10$Y1BsJnU5cU1YFai4AcQZ.ehi7Zq9UqkkYt8HvuswDIBmCDvHtoBRO', 'karen', NULL, 'makaren', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 23:01:55.836346', '2026-04-21 16:45:10.046426', NULL, '2025-10-21 16:45:10.046426', 4, 32, 'kekek76293@ncien.com', 'ACTIF', 'mardi', '{bcrypt}$2a$10$X.krm6m9eW6lXhg9s6BfrulahfIQim78fWisJKONzWbUqlkJ6bk.q', 'bernard', NULL, 'mardi', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-09 15:25:44.714322', '2026-04-21 16:45:35.118595', NULL, '2025-10-21 16:45:35.118595', 4, 26, 'lagobo1334@inupup.com', 'ACTIF', 'mazraoui', '{bcrypt}$2a$10$gKDXC5BEem8cxfj7NLJyFuY9FH/9TJZ5n5R43phGaSCWIm14I02ou', 'noussair', NULL, 'mazraoui', NULL);
INSERT INTO "utilisateur" VALUES ('2025-10-23 10:16:42.048802', '2026-04-23 10:16:42.209665', NULL, '2025-10-23 10:16:42.048802', 3, 77, 'vebime6270@fogdiver.com', 'ACTIF', 'lblanch', '{bcrypt}$2a$10$ULNQlHS5AV4qtyWsvcZc8.LgQofl1NCvDaS3ahSjHDMqV9RS5fsbu', 'hamid', NULL, 'buiba', ',ick,zo');
INSERT INTO "utilisateur" VALUES ('2025-09-09 14:25:05.891033', '2026-03-15 16:29:14.036026', NULL, '2025-10-31 16:47:37.747922', 4, 18, '5zjx9t5hhc@xkxkud.com', 'ACTIF', 'ayayda', '{bcrypt}$2a$10$SI20uuf9BCYPkDRjBIZa1OzjEtynJh4zmD9y/Nv33cwPnkp/sxb5W', 'rahmani', NULL, '3rezam', NULL);
INSERT INTO "utilisateur" VALUES ('2025-09-11 15:01:51.088821', '2026-04-21 16:11:41.216132', NULL, '2025-10-31 18:42:21.003695', 4, 33, 'mevebof175@cspaus.com', 'ACTIF', 'bensalek', '{bcrypt}$2a$10$QhG1JUZ4/l5rbz2grgbvuu3D3wVDdTw3DxViJXkUIRSFzcHOLFua.', 'abderrahmaneAa', NULL, 'abderrahmane', NULL);
INSERT INTO "utilisateur" VALUES ('2025-10-17 15:03:36.921162', '2026-04-30 18:42:49.08619', NULL, '2025-10-31 18:42:49.08619', 2, 63, 'bimana2669@elygifts.com', 'ACTIF', 'marha', '{bcrypt}$2a$10$Uuw6ERlK/b7fSGhw5aON1OJTvAJhCIBnebQw8DpvK1dlpMWXUb4ae', 'ayoub', NULL, 'siauoub', NULL);

-- Table: utilisateurs
