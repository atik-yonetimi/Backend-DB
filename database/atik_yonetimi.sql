--
-- PostgreSQL database dump
--

\restrict VrcDOOfEBEh6gxNRl4Exg7flEnoWCCKqejYOMcRvcDEVOewfz57CAXDoMKRVOhX

-- Dumped from database version 16.13 (Debian 16.13-1.pgdg13+1)
-- Dumped by pg_dump version 16.13 (Debian 16.13-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: collection_result_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.collection_result_enum AS ENUM (
    'DONE',
    'SKIPPED'
);


ALTER TYPE public.collection_result_enum OWNER TO postgres;

--
-- Name: container_status_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.container_status_enum AS ENUM (
    'ACTIVE',
    'INACTIVE'
);


ALTER TYPE public.container_status_enum OWNER TO postgres;

--
-- Name: route_plan_status_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.route_plan_status_enum AS ENUM (
    'ACTIVE',
    'COMPLETED',
    'CANCELLED'
);


ALTER TYPE public.route_plan_status_enum OWNER TO postgres;

--
-- Name: stop_status_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.stop_status_enum AS ENUM (
    'PENDING',
    'ARRIVED',
    'DONE',
    'SKIPPED'
);


ALTER TYPE public.stop_status_enum OWNER TO postgres;

--
-- Name: waste_type_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.waste_type_enum AS ENUM (
    'CAM',
    'PLASTIK',
    'KAGIT',
    'IKINCI_EL_ESYA',
    'METAL'
);


ALTER TYPE public.waste_type_enum OWNER TO postgres;

--
-- Name: reset_weekly_waste_totals(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.reset_weekly_waste_totals() RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE weekly_waste_totals
    SET total_kg = 0,
        week_start = now(),
        week_end = now() + interval '7 days',
        last_updated = now();
END;
$$;


ALTER FUNCTION public.reset_weekly_waste_totals() OWNER TO postgres;

--
-- Name: update_weekly_waste_totals(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_weekly_waste_totals() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_waste_type waste_type_enum;
BEGIN
    IF NEW.result = 'DONE' AND NEW.amount_kg IS NOT NULL THEN

        SELECT waste_type
        INTO v_waste_type
        FROM vehicles
        WHERE id = NEW.vehicle_id;

        UPDATE weekly_waste_totals
        SET total_kg = total_kg + NEW.amount_kg,
            last_updated = now()
        WHERE waste_type = v_waste_type;

    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_weekly_waste_totals() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admins; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admins (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.admins OWNER TO postgres;

--
-- Name: admins_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.admins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.admins_id_seq OWNER TO postgres;

--
-- Name: admins_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.admins_id_seq OWNED BY public.admins.id;


--
-- Name: collections; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.collections (
    id bigint NOT NULL,
    route_stop_id bigint NOT NULL,
    driver_id bigint NOT NULL,
    vehicle_id bigint NOT NULL,
    result public.collection_result_enum NOT NULL,
    amount_kg numeric(10,2),
    skip_reason text,
    collected_at timestamp with time zone NOT NULL,
    gps_lat double precision,
    gps_lng double precision,
    idempotency_key character varying(100),
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_collections_amount_nonnegative CHECK (((amount_kg IS NULL) OR (amount_kg >= (0)::numeric))),
    CONSTRAINT chk_collections_done_requires_amount CHECK ((((result = 'DONE'::public.collection_result_enum) AND (amount_kg IS NOT NULL) AND (skip_reason IS NULL)) OR ((result = 'SKIPPED'::public.collection_result_enum) AND (skip_reason IS NOT NULL)))),
    CONSTRAINT chk_collections_gps_lat CHECK (((gps_lat IS NULL) OR ((gps_lat >= ('-90'::integer)::double precision) AND (gps_lat <= (90)::double precision)))),
    CONSTRAINT chk_collections_gps_lng CHECK (((gps_lng IS NULL) OR ((gps_lng >= ('-180'::integer)::double precision) AND (gps_lng <= (180)::double precision))))
);


ALTER TABLE public.collections OWNER TO postgres;

--
-- Name: collections_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.collections_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.collections_id_seq OWNER TO postgres;

--
-- Name: collections_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.collections_id_seq OWNED BY public.collections.id;


--
-- Name: complaints; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.complaints (
    id bigint NOT NULL,
    guest_name character varying(100) NOT NULL,
    guest_email character varying(150) NOT NULL,
    subject character varying(150) NOT NULL,
    message text NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_complaints_email_format CHECK (((guest_email)::text ~~ '%@%'::text)),
    CONSTRAINT chk_complaints_guest_email_not_empty CHECK ((length(TRIM(BOTH FROM guest_email)) > 0)),
    CONSTRAINT chk_complaints_guest_name_not_empty CHECK ((length(TRIM(BOTH FROM guest_name)) > 0)),
    CONSTRAINT chk_complaints_message_not_empty CHECK ((length(TRIM(BOTH FROM message)) > 0)),
    CONSTRAINT chk_complaints_subject_not_empty CHECK ((length(TRIM(BOTH FROM subject)) > 0))
);


ALTER TABLE public.complaints OWNER TO postgres;

--
-- Name: complaints_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.complaints_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.complaints_id_seq OWNER TO postgres;

--
-- Name: complaints_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.complaints_id_seq OWNED BY public.complaints.id;


--
-- Name: containers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.containers (
    id bigint NOT NULL,
    waste_type public.waste_type_enum NOT NULL,
    lat double precision NOT NULL,
    lng double precision NOT NULL,
    status public.container_status_enum DEFAULT 'ACTIVE'::public.container_status_enum NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_containers_lat CHECK (((lat >= ('-90'::integer)::double precision) AND (lat <= (90)::double precision))),
    CONSTRAINT chk_containers_lng CHECK (((lng >= ('-180'::integer)::double precision) AND (lng <= (180)::double precision)))
);


ALTER TABLE public.containers OWNER TO postgres;

--
-- Name: containers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.containers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.containers_id_seq OWNER TO postgres;

--
-- Name: containers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.containers_id_seq OWNED BY public.containers.id;


--
-- Name: drivers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.drivers (
    id bigint NOT NULL,
    plate_login character varying(20) NOT NULL,
    assigned_vehicle_id bigint NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.drivers OWNER TO postgres;

--
-- Name: drivers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.drivers_id_seq OWNER TO postgres;

--
-- Name: drivers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.drivers_id_seq OWNED BY public.drivers.id;


--
-- Name: guests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guests (
    id bigint NOT NULL,
    name character varying(100),
    created_at timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.guests OWNER TO postgres;

--
-- Name: guests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.guests_id_seq OWNER TO postgres;

--
-- Name: guests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guests_id_seq OWNED BY public.guests.id;


--
-- Name: telemetry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.telemetry (
    id bigint NOT NULL,
    container_id bigint NOT NULL,
    fill_percent numeric(5,2) NOT NULL,
    lat double precision NOT NULL,
    lng double precision NOT NULL,
    source_timestamp timestamp with time zone NOT NULL,
    ingested_at timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_telemetry_fill_percent CHECK (((fill_percent >= (0)::numeric) AND (fill_percent <= (100)::numeric))),
    CONSTRAINT chk_telemetry_lat CHECK (((lat >= ('-90'::integer)::double precision) AND (lat <= (90)::double precision))),
    CONSTRAINT chk_telemetry_lng CHECK (((lng >= ('-180'::integer)::double precision) AND (lng <= (180)::double precision)))
);


ALTER TABLE public.telemetry OWNER TO postgres;

--
-- Name: latest_container_state; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.latest_container_state AS
 SELECT DISTINCT ON (t.container_id) t.container_id,
    c.waste_type,
    t.fill_percent,
    t.lat,
    t.lng,
    t.source_timestamp,
    t.ingested_at
   FROM (public.telemetry t
     JOIN public.containers c ON ((c.id = t.container_id)))
  ORDER BY t.container_id, t.source_timestamp DESC, t.id DESC;


ALTER VIEW public.latest_container_state OWNER TO postgres;

--
-- Name: route_plans; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.route_plans (
    id bigint NOT NULL,
    vehicle_id bigint NOT NULL,
    waste_type public.waste_type_enum NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    status public.route_plan_status_enum DEFAULT 'ACTIVE'::public.route_plan_status_enum NOT NULL
);


ALTER TABLE public.route_plans OWNER TO postgres;

--
-- Name: route_plans_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.route_plans_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.route_plans_id_seq OWNER TO postgres;

--
-- Name: route_plans_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.route_plans_id_seq OWNED BY public.route_plans.id;


--
-- Name: route_stops; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.route_stops (
    id bigint NOT NULL,
    route_plan_id bigint NOT NULL,
    container_id bigint NOT NULL,
    sequence_no integer NOT NULL,
    status public.stop_status_enum DEFAULT 'PENDING'::public.stop_status_enum NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    CONSTRAINT chk_route_stops_sequence_no CHECK ((sequence_no > 0))
);


ALTER TABLE public.route_stops OWNER TO postgres;

--
-- Name: route_stops_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.route_stops_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.route_stops_id_seq OWNER TO postgres;

--
-- Name: route_stops_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.route_stops_id_seq OWNED BY public.route_stops.id;


--
-- Name: telemetry_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.telemetry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.telemetry_id_seq OWNER TO postgres;

--
-- Name: telemetry_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.telemetry_id_seq OWNED BY public.telemetry.id;


--
-- Name: vehicles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicles (
    id bigint NOT NULL,
    plate character varying(20) NOT NULL,
    waste_type public.waste_type_enum NOT NULL,
    garage_lat double precision NOT NULL,
    garage_lng double precision NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    login_password character varying(100),
    CONSTRAINT chk_vehicles_garage_lat CHECK (((garage_lat >= ('-90'::integer)::double precision) AND (garage_lat <= (90)::double precision))),
    CONSTRAINT chk_vehicles_garage_lng CHECK (((garage_lng >= ('-180'::integer)::double precision) AND (garage_lng <= (180)::double precision)))
);


ALTER TABLE public.vehicles OWNER TO postgres;

--
-- Name: vehicles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vehicles_id_seq OWNER TO postgres;

--
-- Name: vehicles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicles_id_seq OWNED BY public.vehicles.id;


--
-- Name: weekly_waste_totals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.weekly_waste_totals (
    id bigint NOT NULL,
    waste_type public.waste_type_enum NOT NULL,
    total_kg numeric(10,2) DEFAULT 0 NOT NULL,
    week_start timestamp with time zone DEFAULT now() NOT NULL,
    week_end timestamp with time zone DEFAULT (now() + '7 days'::interval) NOT NULL,
    last_updated timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.weekly_waste_totals OWNER TO postgres;

--
-- Name: weekly_waste_totals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.weekly_waste_totals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.weekly_waste_totals_id_seq OWNER TO postgres;

--
-- Name: weekly_waste_totals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.weekly_waste_totals_id_seq OWNED BY public.weekly_waste_totals.id;


--
-- Name: admins id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admins ALTER COLUMN id SET DEFAULT nextval('public.admins_id_seq'::regclass);


--
-- Name: collections id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collections ALTER COLUMN id SET DEFAULT nextval('public.collections_id_seq'::regclass);


--
-- Name: complaints id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complaints ALTER COLUMN id SET DEFAULT nextval('public.complaints_id_seq'::regclass);


--
-- Name: containers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.containers ALTER COLUMN id SET DEFAULT nextval('public.containers_id_seq'::regclass);


--
-- Name: drivers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers ALTER COLUMN id SET DEFAULT nextval('public.drivers_id_seq'::regclass);


--
-- Name: guests id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guests ALTER COLUMN id SET DEFAULT nextval('public.guests_id_seq'::regclass);


--
-- Name: route_plans id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_plans ALTER COLUMN id SET DEFAULT nextval('public.route_plans_id_seq'::regclass);


--
-- Name: route_stops id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_stops ALTER COLUMN id SET DEFAULT nextval('public.route_stops_id_seq'::regclass);


--
-- Name: telemetry id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telemetry ALTER COLUMN id SET DEFAULT nextval('public.telemetry_id_seq'::regclass);


--
-- Name: vehicles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles ALTER COLUMN id SET DEFAULT nextval('public.vehicles_id_seq'::regclass);


--
-- Name: weekly_waste_totals id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weekly_waste_totals ALTER COLUMN id SET DEFAULT nextval('public.weekly_waste_totals_id_seq'::regclass);


--
-- Data for Name: admins; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.admins (id, username, password, created_at) FROM stdin;
1	admin1	adminpass	2026-04-25 20:27:24.679363+00
2	admin2	adminpass2	2026-04-25 20:27:24.679363+00
\.


--
-- Data for Name: collections; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.collections (id, route_stop_id, driver_id, vehicle_id, result, amount_kg, skip_reason, collected_at, gps_lat, gps_lng, idempotency_key, created_at) FROM stdin;
1	1	1	1	DONE	12.50	\N	2026-03-20 16:43:49.238268+00	37.0001	35.3213	col-001	2026-03-20 16:43:49.238268+00
\.


--
-- Data for Name: complaints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.complaints (id, guest_name, guest_email, subject, message, created_at) FROM stdin;
\.


--
-- Data for Name: containers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.containers (id, waste_type, lat, lng, status, created_at) FROM stdin;
1	CAM	37.58771	36.832294	ACTIVE	2026-03-20 16:42:22.730843+00
2	PLASTIK	37.58771	36.832294	ACTIVE	2026-03-20 16:42:22.730843+00
3	CAM	37.586163	36.827445	ACTIVE	2026-04-25 20:35:46.349218+00
4	PLASTIK	37.586163	36.827445	ACTIVE	2026-04-25 20:35:46.349218+00
5	KAGIT	37.58771	36.832294	ACTIVE	2026-04-25 20:35:46.349218+00
6	IKINCI_EL_ESYA	37.58771	36.832294	ACTIVE	2026-04-25 20:35:46.349218+00
7	METAL	37.58771	36.832294	ACTIVE	2026-04-25 20:35:46.349218+00
8	CAM	37.585568	36.82443	ACTIVE	2026-04-25 20:35:46.349218+00
9	PLASTIK	37.585568	36.82443	ACTIVE	2026-04-25 20:35:46.349218+00
10	KAGIT	37.586163	36.827445	ACTIVE	2026-04-25 20:35:46.349218+00
11	IKINCI_EL_ESYA	37.586163	36.827445	ACTIVE	2026-04-25 20:35:46.349218+00
12	METAL	37.586163	36.827445	ACTIVE	2026-04-25 20:35:46.349218+00
13	CAM	37.586392	36.819903	ACTIVE	2026-04-25 20:35:46.349218+00
14	PLASTIK	37.586392	36.819903	ACTIVE	2026-04-25 20:35:46.349218+00
15	KAGIT	37.585568	36.82443	ACTIVE	2026-04-25 20:35:46.349218+00
16	IKINCI_EL_ESYA	37.585568	36.82443	ACTIVE	2026-04-25 20:35:46.349218+00
17	METAL	37.585568	36.82443	ACTIVE	2026-04-25 20:35:46.349218+00
18	CAM	37.587982	36.817832	ACTIVE	2026-04-25 20:35:46.349218+00
19	PLASTIK	37.587982	36.817832	ACTIVE	2026-04-25 20:35:46.349218+00
20	KAGIT	37.586392	36.819903	ACTIVE	2026-04-25 20:35:46.349218+00
21	IKINCI_EL_ESYA	37.586392	36.819903	ACTIVE	2026-04-25 20:35:46.349218+00
22	METAL	37.586392	36.819903	ACTIVE	2026-04-25 20:35:46.349218+00
23	CAM	37.588187	36.812682	ACTIVE	2026-04-25 20:35:46.349218+00
24	PLASTIK	37.588187	36.812682	ACTIVE	2026-04-25 20:35:46.349218+00
25	KAGIT	37.587982	36.817832	ACTIVE	2026-04-25 20:35:46.349218+00
26	IKINCI_EL_ESYA	37.587982	36.817832	ACTIVE	2026-04-25 20:35:46.349218+00
27	METAL	37.587982	36.817832	ACTIVE	2026-04-25 20:35:46.349218+00
28	CAM	37.585806	36.808187	ACTIVE	2026-04-25 20:35:46.349218+00
29	PLASTIK	37.585806	36.808187	ACTIVE	2026-04-25 20:35:46.349218+00
30	KAGIT	37.588187	36.812682	ACTIVE	2026-04-25 20:35:46.349218+00
31	IKINCI_EL_ESYA	37.588187	36.812682	ACTIVE	2026-04-25 20:35:46.349218+00
32	METAL	37.588187	36.812682	ACTIVE	2026-04-25 20:35:46.349218+00
33	CAM	37.585712	36.81236	ACTIVE	2026-04-25 20:35:46.349218+00
34	PLASTIK	37.585712	36.81236	ACTIVE	2026-04-25 20:35:46.349218+00
35	KAGIT	37.585806	36.808187	ACTIVE	2026-04-25 20:35:46.349218+00
36	IKINCI_EL_ESYA	37.585806	36.808187	ACTIVE	2026-04-25 20:35:46.349218+00
37	METAL	37.585806	36.808187	ACTIVE	2026-04-25 20:35:46.349218+00
38	CAM	37.585049	36.818122	ACTIVE	2026-04-25 20:35:46.349218+00
39	PLASTIK	37.585049	36.818122	ACTIVE	2026-04-25 20:35:46.349218+00
40	KAGIT	37.585712	36.81236	ACTIVE	2026-04-25 20:35:46.349218+00
41	IKINCI_EL_ESYA	37.585712	36.81236	ACTIVE	2026-04-25 20:35:46.349218+00
42	METAL	37.585712	36.81236	ACTIVE	2026-04-25 20:35:46.349218+00
43	CAM	37.585219	36.824859	ACTIVE	2026-04-25 20:35:46.349218+00
44	PLASTIK	37.585219	36.824859	ACTIVE	2026-04-25 20:35:46.349218+00
45	KAGIT	37.585049	36.818122	ACTIVE	2026-04-25 20:35:46.349218+00
46	IKINCI_EL_ESYA	37.585049	36.818122	ACTIVE	2026-04-25 20:35:46.349218+00
47	METAL	37.585049	36.818122	ACTIVE	2026-04-25 20:35:46.349218+00
48	CAM	37.587039	36.829881	ACTIVE	2026-04-25 20:35:46.349218+00
49	PLASTIK	37.587039	36.829881	ACTIVE	2026-04-25 20:35:46.349218+00
50	KAGIT	37.585219	36.824859	ACTIVE	2026-04-25 20:35:46.349218+00
51	IKINCI_EL_ESYA	37.585219	36.824859	ACTIVE	2026-04-25 20:35:46.349218+00
52	METAL	37.585219	36.824859	ACTIVE	2026-04-25 20:35:46.349218+00
53	CAM	37.587073	36.813927	ACTIVE	2026-04-25 20:35:46.349218+00
54	PLASTIK	37.587073	36.813927	ACTIVE	2026-04-25 20:35:46.349218+00
55	KAGIT	37.587039	36.829881	ACTIVE	2026-04-25 20:35:46.349218+00
56	IKINCI_EL_ESYA	37.587039	36.829881	ACTIVE	2026-04-25 20:35:46.349218+00
57	METAL	37.587039	36.829881	ACTIVE	2026-04-25 20:35:46.349218+00
58	CAM	37.58811	36.807886	ACTIVE	2026-04-25 20:35:46.349218+00
59	PLASTIK	37.58811	36.807886	ACTIVE	2026-04-25 20:35:46.349218+00
60	KAGIT	37.587073	36.813927	ACTIVE	2026-04-25 20:35:46.349218+00
61	IKINCI_EL_ESYA	37.587073	36.813927	ACTIVE	2026-04-25 20:35:46.349218+00
62	METAL	37.587073	36.813927	ACTIVE	2026-04-25 20:35:46.349218+00
63	CAM	37.585661	36.815246	ACTIVE	2026-04-25 20:35:46.349218+00
64	PLASTIK	37.585661	36.815246	ACTIVE	2026-04-25 20:35:46.349218+00
65	KAGIT	37.58811	36.807886	ACTIVE	2026-04-25 20:35:46.349218+00
66	IKINCI_EL_ESYA	37.58811	36.807886	ACTIVE	2026-04-25 20:35:46.349218+00
67	METAL	37.58811	36.807886	ACTIVE	2026-04-25 20:35:46.349218+00
68	CAM	37.589474	36.816077	ACTIVE	2026-04-25 20:35:46.349218+00
69	PLASTIK	37.589474	36.816077	ACTIVE	2026-04-25 20:35:46.349218+00
70	KAGIT	37.585661	36.815246	ACTIVE	2026-04-25 20:35:46.349218+00
71	IKINCI_EL_ESYA	37.585661	36.815246	ACTIVE	2026-04-25 20:35:46.349218+00
72	METAL	37.585661	36.815246	ACTIVE	2026-04-25 20:35:46.349218+00
75	KAGIT	37.589474	36.816077	ACTIVE	2026-04-25 20:35:46.349218+00
76	IKINCI_EL_ESYA	37.589474	36.816077	ACTIVE	2026-04-25 20:35:46.349218+00
77	METAL	37.589474	36.816077	ACTIVE	2026-04-25 20:35:46.349218+00
\.


--
-- Data for Name: drivers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.drivers (id, plate_login, assigned_vehicle_id, created_at) FROM stdin;
2	01ABC02	2	2026-03-20 16:42:33.916686+00
7	01ABC006	4	2026-04-26 18:44:04.921831+00
8	01ABC007	8	2026-04-26 18:44:04.921831+00
11	01ABC010	3	2026-04-26 18:44:04.921831+00
1	01ABC03	1	2026-03-20 16:42:33.916686+00
\.


--
-- Data for Name: guests; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.guests (id, name, created_at) FROM stdin;
1	Misafir Kullanıcı 1	2026-04-25 20:27:28.451961+00
2	Misafir Kullanıcı 2	2026-04-25 20:27:28.451961+00
\.


--
-- Data for Name: route_plans; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.route_plans (id, vehicle_id, waste_type, created_at, status) FROM stdin;
1	1	CAM	2026-03-20 16:43:39.522996+00	ACTIVE
\.


--
-- Data for Name: route_stops; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.route_stops (id, route_plan_id, container_id, sequence_no, status, created_at) FROM stdin;
1	1	1	1	PENDING	2026-03-20 16:43:44.274652+00
\.


--
-- Data for Name: telemetry; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.telemetry (id, container_id, fill_percent, lat, lng, source_timestamp, ingested_at) FROM stdin;
1	1	45.00	37.0001	35.3213	2026-03-06 10:00:00+00	2026-03-20 16:42:40.102033+00
2	1	72.00	37.0001	35.3213	2026-03-06 11:00:00+00	2026-03-20 16:42:40.102033+00
3	2	61.00	37.001	35.322	2026-03-06 10:30:00+00	2026-03-20 16:42:40.102033+00
4	1	86.89	37.58771	36.832294	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
5	3	90.48	37.586163	36.827445	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
6	8	88.68	37.585568	36.82443	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
7	13	73.03	37.586392	36.819903	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
8	18	94.02	37.587982	36.817832	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
9	2	74.44	37.58771	36.832294	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
10	4	71.59	37.586163	36.827445	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
11	9	62.71	37.585568	36.82443	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
12	14	93.90	37.586392	36.819903	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
13	19	75.93	37.587982	36.817832	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
14	5	81.52	37.58771	36.832294	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
15	10	81.35	37.586163	36.827445	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
16	15	82.55	37.585568	36.82443	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
17	20	64.93	37.586392	36.819903	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
18	25	82.98	37.587982	36.817832	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
19	6	65.03	37.58771	36.832294	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
20	11	85.64	37.586163	36.827445	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
21	16	65.97	37.585568	36.82443	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
22	21	69.61	37.586392	36.819903	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
23	26	93.37	37.587982	36.817832	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
24	7	78.52	37.58771	36.832294	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
25	12	82.17	37.586163	36.827445	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
26	17	66.75	37.585568	36.82443	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
27	22	63.26	37.586392	36.819903	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
28	27	90.03	37.587982	36.817832	2026-04-26 18:04:09.892211+00	2026-04-26 18:04:09.892211+00
29	46	84.00	37.585049	36.818122	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
30	45	37.00	37.585049	36.818122	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
31	38	29.00	37.585049	36.818122	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
32	39	33.00	37.585049	36.818122	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
33	47	87.00	37.585049	36.818122	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
34	50	88.00	37.585219	36.824859	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
35	43	34.00	37.585219	36.824859	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
36	44	38.00	37.585219	36.824859	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
37	52	94.00	37.585219	36.824859	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
38	51	91.00	37.585219	36.824859	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
39	16	51.00	37.585568	36.82443	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
40	8	39.00	37.585568	36.82443	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
41	9	92.00	37.585568	36.82443	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
42	15	65.00	37.585568	36.82443	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
43	17	20.00	37.585568	36.82443	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
44	71	21.00	37.585661	36.815246	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
45	72	25.00	37.585661	36.815246	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
46	70	72.00	37.585661	36.815246	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
47	64	69.00	37.585661	36.815246	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
48	63	66.00	37.585661	36.815246	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
49	42	85.00	37.585712	36.81236	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
50	33	73.00	37.585712	36.81236	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
51	34	53.00	37.585712	36.81236	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
52	40	22.00	37.585712	36.81236	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
53	41	26.00	37.585712	36.81236	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
54	29	23.00	37.585806	36.808187	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
55	36	89.00	37.585806	36.808187	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
56	28	80.00	37.585806	36.808187	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
57	35	27.00	37.585806	36.808187	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
58	37	92.00	37.585806	36.808187	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
59	3	24.00	37.586163	36.827445	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
60	4	28.00	37.586163	36.827445	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
61	10	93.00	37.586163	36.827445	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
62	11	66.00	37.586163	36.827445	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
63	12	40.00	37.586163	36.827445	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
64	22	45.00	37.586392	36.819903	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
65	21	73.00	37.586392	36.819903	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
66	13	29.00	37.586392	36.819903	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
67	14	67.00	37.586392	36.819903	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
68	20	70.00	37.586392	36.819903	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
69	56	46.00	37.587039	36.829881	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
70	57	50.00	37.587039	36.829881	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
71	55	42.00	37.587039	36.829881	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
72	48	71.00	37.587039	36.829881	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
73	49	74.00	37.587039	36.829881	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
74	53	78.00	37.587073	36.813927	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
75	54	81.00	37.587073	36.813927	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
76	62	90.00	37.587073	36.813927	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
77	61	51.00	37.587073	36.813927	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
78	60	47.00	37.587073	36.813927	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
79	1	44.00	37.58771	36.832294	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
80	2	48.00	37.58771	36.832294	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
81	5	52.00	37.58771	36.832294	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
82	6	94.00	37.58771	36.832294	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
83	7	67.00	37.58771	36.832294	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
84	27	74.00	37.587982	36.817832	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
85	26	71.00	37.587982	36.817832	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
86	25	68.00	37.587982	36.817832	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
87	19	53.00	37.587982	36.817832	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
88	18	49.00	37.587982	36.817832	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
89	65	75.00	37.58811	36.807886	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
90	67	35.00	37.58811	36.807886	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
91	58	54.00	37.58811	36.807886	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
92	59	72.00	37.58811	36.807886	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
93	66	31.00	37.58811	36.807886	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
94	32	40.00	37.588187	36.812682	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
95	31	36.00	37.588187	36.812682	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
96	30	82.00	37.588187	36.812682	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
97	24	79.00	37.588187	36.812682	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
98	23	76.00	37.588187	36.812682	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
99	69	33.00	37.589474	36.816077	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
100	68	83.00	37.589474	36.816077	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
101	75	37.00	37.589474	36.816077	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
102	76	41.00	37.589474	36.816077	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
103	77	65.00	37.589474	36.816077	2026-04-26 20:52:29.813242+00	2026-04-26 20:52:29.813242+00
\.


--
-- Data for Name: vehicles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vehicles (id, plate, waste_type, garage_lat, garage_lng, created_at, login_password) FROM stdin;
2	01ABC02	PLASTIK	36.99	35.3	2026-03-20 16:42:27.784072+00	plastik01pass
4	01ABC006	KAGIT	37.0005	35.3218	2026-04-25 20:00:31.011959+00	kagit02pass
8	01ABC007	IKINCI_EL_ESYA	37.0006	35.3219	2026-04-25 20:00:31.011959+00	esya01pass
3	01ABC010	METAL	37.0009	35.3222	2026-04-25 20:00:31.011959+00	metal02pass
1	01ABC03	CAM	36.99	35.3	2026-03-20 16:42:27.784072+00	cam03pass
\.


--
-- Data for Name: weekly_waste_totals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.weekly_waste_totals (id, waste_type, total_kg, week_start, week_end, last_updated) FROM stdin;
1	CAM	0.00	2026-04-25 21:06:09.784089+00	2026-05-02 21:06:09.784089+00	2026-04-25 21:06:09.784089+00
2	PLASTIK	0.00	2026-04-25 21:06:09.784089+00	2026-05-02 21:06:09.784089+00	2026-04-25 21:06:09.784089+00
3	KAGIT	0.00	2026-04-25 21:06:09.784089+00	2026-05-02 21:06:09.784089+00	2026-04-25 21:06:09.784089+00
4	IKINCI_EL_ESYA	0.00	2026-04-25 21:06:09.784089+00	2026-05-02 21:06:09.784089+00	2026-04-25 21:06:09.784089+00
5	METAL	0.00	2026-04-25 21:06:09.784089+00	2026-05-02 21:06:09.784089+00	2026-04-25 21:06:09.784089+00
\.


--
-- Name: admins_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.admins_id_seq', 2, true);


--
-- Name: collections_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.collections_id_seq', 2, true);


--
-- Name: complaints_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.complaints_id_seq', 1, true);


--
-- Name: containers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.containers_id_seq', 77, true);


--
-- Name: drivers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.drivers_id_seq', 11, true);


--
-- Name: guests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.guests_id_seq', 2, true);


--
-- Name: route_plans_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.route_plans_id_seq', 1, true);


--
-- Name: route_stops_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.route_stops_id_seq', 1, true);


--
-- Name: telemetry_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.telemetry_id_seq', 103, true);


--
-- Name: vehicles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicles_id_seq', 13, true);


--
-- Name: weekly_waste_totals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.weekly_waste_totals_id_seq', 5, true);


--
-- Name: admins admins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admins
    ADD CONSTRAINT admins_pkey PRIMARY KEY (id);


--
-- Name: admins admins_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admins
    ADD CONSTRAINT admins_username_key UNIQUE (username);


--
-- Name: collections collections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_pkey PRIMARY KEY (id);


--
-- Name: complaints complaints_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complaints
    ADD CONSTRAINT complaints_pkey PRIMARY KEY (id);


--
-- Name: containers containers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.containers
    ADD CONSTRAINT containers_pkey PRIMARY KEY (id);


--
-- Name: drivers drivers_assigned_vehicle_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_assigned_vehicle_id_key UNIQUE (assigned_vehicle_id);


--
-- Name: drivers drivers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pkey PRIMARY KEY (id);


--
-- Name: drivers drivers_plate_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_plate_login_key UNIQUE (plate_login);


--
-- Name: guests guests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guests
    ADD CONSTRAINT guests_pkey PRIMARY KEY (id);


--
-- Name: route_plans route_plans_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_plans
    ADD CONSTRAINT route_plans_pkey PRIMARY KEY (id);


--
-- Name: route_stops route_stops_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_stops
    ADD CONSTRAINT route_stops_pkey PRIMARY KEY (id);


--
-- Name: telemetry telemetry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telemetry
    ADD CONSTRAINT telemetry_pkey PRIMARY KEY (id);


--
-- Name: route_stops uq_route_stops_route_container; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_stops
    ADD CONSTRAINT uq_route_stops_route_container UNIQUE (route_plan_id, container_id);


--
-- Name: route_stops uq_route_stops_route_sequence; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_stops
    ADD CONSTRAINT uq_route_stops_route_sequence UNIQUE (route_plan_id, sequence_no);


--
-- Name: vehicles vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_pkey PRIMARY KEY (id);


--
-- Name: vehicles vehicles_plate_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_plate_key UNIQUE (plate);


--
-- Name: weekly_waste_totals weekly_waste_totals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weekly_waste_totals
    ADD CONSTRAINT weekly_waste_totals_pkey PRIMARY KEY (id);


--
-- Name: idx_collections_route_stop; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_collections_route_stop ON public.collections USING btree (route_stop_id);


--
-- Name: idx_route_plans_vehicle_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_route_plans_vehicle_status ON public.route_plans USING btree (vehicle_id, status, created_at DESC);


--
-- Name: idx_route_stops_route_sequence; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_route_stops_route_sequence ON public.route_stops USING btree (route_plan_id, sequence_no);


--
-- Name: idx_telemetry_container_source_ts; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_telemetry_container_source_ts ON public.telemetry USING btree (container_id, source_timestamp DESC, id DESC);


--
-- Name: uq_collections_idempotency_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX uq_collections_idempotency_key ON public.collections USING btree (idempotency_key) WHERE (idempotency_key IS NOT NULL);


--
-- Name: collections trg_update_weekly_waste_totals; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_update_weekly_waste_totals AFTER INSERT ON public.collections FOR EACH ROW EXECUTE FUNCTION public.update_weekly_waste_totals();


--
-- Name: collections collections_driver_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_driver_id_fkey FOREIGN KEY (driver_id) REFERENCES public.drivers(id) ON DELETE RESTRICT;


--
-- Name: collections collections_route_stop_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_route_stop_id_fkey FOREIGN KEY (route_stop_id) REFERENCES public.route_stops(id) ON DELETE RESTRICT;


--
-- Name: collections collections_vehicle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES public.vehicles(id) ON DELETE RESTRICT;


--
-- Name: drivers drivers_assigned_vehicle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_assigned_vehicle_id_fkey FOREIGN KEY (assigned_vehicle_id) REFERENCES public.vehicles(id) ON DELETE RESTRICT;


--
-- Name: route_plans route_plans_vehicle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_plans
    ADD CONSTRAINT route_plans_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES public.vehicles(id) ON DELETE RESTRICT;


--
-- Name: route_stops route_stops_container_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_stops
    ADD CONSTRAINT route_stops_container_id_fkey FOREIGN KEY (container_id) REFERENCES public.containers(id) ON DELETE RESTRICT;


--
-- Name: route_stops route_stops_route_plan_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route_stops
    ADD CONSTRAINT route_stops_route_plan_id_fkey FOREIGN KEY (route_plan_id) REFERENCES public.route_plans(id) ON DELETE CASCADE;


--
-- Name: telemetry telemetry_container_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.telemetry
    ADD CONSTRAINT telemetry_container_id_fkey FOREIGN KEY (container_id) REFERENCES public.containers(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

\unrestrict VrcDOOfEBEh6gxNRl4Exg7flEnoWCCKqejYOMcRvcDEVOewfz57CAXDoMKRVOhX

