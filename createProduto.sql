CREATE TABLE IF NOT EXISTS public.produto
(
    id integer NOT NULL DEFAULT nextval('id_prd_seq'::regclass),
    hash uuid DEFAULT gen_random_uuid(),
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descricao text COLLATE pg_catalog."default",
    ean13 character varying(13) COLLATE pg_catalog."default",
    preco numeric(13,2) NOT NULL DEFAULT 0.00,
    quantidade numeric(13,2) NOT NULL DEFAULT 0,
    estoque_min numeric(13,2) NOT NULL DEFAULT 0,
    dtcreate timestamp with time zone DEFAULT now(),
    dtupdate timestamp with time zone,
    lativo boolean DEFAULT false,
    CONSTRAINT produto_pkey PRIMARY KEY (id),
    CONSTRAINT produto_ean13_key UNIQUE (ean13),
    CONSTRAINT produto_nome_key UNIQUE (nome),
    CONSTRAINT produto_estoque_min_check CHECK (estoque_min >= 0::numeric),
    CONSTRAINT produto_preco_check CHECK (preco >= 0::numeric),
    CONSTRAINT produto_quantidade_check CHECK (quantidade >= 0::numeric)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.produto
    OWNER to postgres;