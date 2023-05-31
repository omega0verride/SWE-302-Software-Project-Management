package com.redscooter.config;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.query.sqm.function.NamedSqmFunctionDescriptor;
import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;

import java.util.List;

public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {
    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction("fts",
                FTSFunction.INSTANCE);
        metadataBuilder.applySqlFunction("custom_ts_rank",
                CustomTSRankFunction.INSTANCE);

    }

    static class FTSFunction extends NamedSqmFunctionDescriptor {

        public static final FTSFunction INSTANCE = new FTSFunction();

        public FTSFunction() {
            super(
                    "fts",
                    false,
                    StandardArgumentsValidators.exactly(1),
                    null
            );
        }

        public void render(
                SqlAppender sqlAppender,
                List<? extends SqlAstNode> arguments,
                SqlAstTranslator<?> walker) {
            Expression text = (Expression) arguments.get(0);
            sqlAppender.appendSql("search_vector @@ to_tsquery(regexp_replace(cast(plainto_tsquery(f_unaccent(");
            walker.render(text, SqlAstNodeRenderingMode.DEFAULT);
            sqlAppender.appendSql(")) as text), E'(\\'\\\\w+\\')', E'\\\\1:*', 'g'))");
        }
    }

    static class CustomTSRankFunction extends NamedSqmFunctionDescriptor {
        public static final CustomTSRankFunction INSTANCE = new CustomTSRankFunction();
        public CustomTSRankFunction() {
            super(
                    "custom_ts_rank",
                    false,
                    StandardArgumentsValidators.exactly(1),
                    null
            );
        }

        public void render(
                SqlAppender sqlAppender,
                List<? extends SqlAstNode> arguments,
                SqlAstTranslator<?> walker) {
            Expression text = (Expression) arguments.get(0);
            sqlAppender.appendSql("ts_rank(search_vector, plainto_tsquery(f_unaccent(");
            walker.render(text, SqlAstNodeRenderingMode.DEFAULT);
            sqlAppender.appendSql(")))");
        }
    }

}
