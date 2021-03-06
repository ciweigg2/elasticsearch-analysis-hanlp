package com.hankcs.lucene;

import static org.elasticsearch.plugin.analysis.AnalysisHanLPPlugin.defaultStopWordDictionary;

import com.hankcs.hanlp.HanLP;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import java.util.Set;

public class HanLPIndexAnalyzer extends Analyzer {

    private boolean pstemming;
    private Set<String> filter;

    /**
     * @param filter    停用词
     * @param pstemming 是否分析词干
     */
    public HanLPIndexAnalyzer(Set<String> filter, boolean pstemming) {
        this.filter = filter;
        this.pstemming = pstemming;
    }

    /**
     * @param pstemming 是否分析词干.进行单复数,时态的转换
     */
    public HanLPIndexAnalyzer(boolean pstemming) {
        this.pstemming = pstemming;
        this.filter = defaultStopWordDictionary;
    }

    public HanLPIndexAnalyzer() {
        super();
        this.filter = defaultStopWordDictionary;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new HanLPTokenizer(HanLP.newSegment().enableIndexMode(true).enableOffset(true), filter, pstemming);
        return new TokenStreamComponents(tokenizer);
    }
}
